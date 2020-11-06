/*
 * Copyright 2020 Fluence Labs Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#![allow(dead_code)]

use crate::{aquamarine_fname, now, uuid, Instruction};

use host_closure::Args;
use ivalue_utils::{IType, IValue};
use particle_actors::HostImportDescriptor;
use particle_protocol::Particle;

use aquamarine_vm::{AquamarineVM, AquamarineVMConfig, HostExportedFunc, StepperOutcome};

use fluence_libp2p::RandomPeerId;
use fstrings::f;
use libp2p::PeerId;
use parking_lot::Mutex;
use serde_json::Value as JValue;
use std::cell::RefCell;
use std::{collections::HashMap, ops::Deref, sync::Arc};

pub const LOCAL_VM: &'static str = "12D3KooWGBW97ZLQ7tMkMmBgqpgn8P5gw7WNthjE5PjvzBZKa7VM";

thread_local! {
    static VM: RefCell<SingletonVM> = RefCell::new(SingletonVM::new());
}

type LoadMap = Arc<Mutex<HashMap<String, JValue>>>;
type ReturnMap = Arc<Mutex<HashMap<String, Vec<JValue>>>>;

struct SingletonVM {
    vm: AquamarineVM,
    pub peer_id: PeerId,
    load_map: LoadMap,
    return_map: ReturnMap,
}

impl SingletonVM {
    pub fn new() -> Self {
        let peer_id = RandomPeerId::random();
        let load_map: LoadMap = <_>::default();
        let return_map: ReturnMap = <_>::default();
        let load = load_map.clone();
        let ret = return_map.clone();
        let host_func: HostExportedFunc =
            Box::new(move |_, args| route(args, load.clone(), ret.clone()));
        let vm = make_vm(host_func);
        Self {
            vm,
            peer_id,
            load_map,
            return_map,
        }
    }

    pub fn make_particle(
        &mut self,
        data: HashMap<&'static str, JValue>,
        script: String,
        init_peer_id: PeerId,
    ) -> Particle {
        let variable_names = data.keys().cloned().collect::<Vec<_>>();

        let load_variables = variable_names
            .into_iter()
            .map(|name| f!(r#"(call "{LOCAL_VM}" ("load" "{name}") [] {name})"#))
            .fold(Instruction::Null, |acc, call| acc.add(call))
            .into_air();
        let script = f!(r#"
(seq
    {load_variables}
    {script}
)
    "#);

        self.load_map
            .lock()
            .extend(data.into_iter().map(|(k, v)| (k.to_string(), v)));

        let id = uuid();
        let StepperOutcome { data, .. } = self
            .vm
            .call(LOCAL_VM.to_string(), script.clone(), "[]", id.clone())
            .expect("execute & make particle");

        log::warn!("Made a particle {}, data: {}", id, data);

        Particle {
            id,
            init_peer_id,
            timestamp: now(),
            ttl: 10000,
            script,
            signature: vec![],
            data: serde_json::from_str(&data).expect("valid json"),
            // data: serde_json::from_str::<'_, JValue>(&data).expect("valid json")["data"].take(),
        }
    }

    pub fn read_args(
        &mut self,
        particle: Particle,
        peer_id: &PeerId,
        key: &str,
    ) -> Option<Vec<JValue>> {
        log::warn!("read_args from particle {:#?}", particle);
        self.vm
            .call(
                peer_id.to_string(),
                particle.script,
                particle.data.to_string(),
                particle.id,
            )
            .expect("execute read_args vm");
        self.return_map.lock().get(key).cloned()
    }
}

fn make_vm(host_func: HostExportedFunc) -> AquamarineVM {
    let call_service = HostImportDescriptor {
        host_exported_func: host_func,
        argument_types: vec![IType::String, IType::String, IType::String],
        output_type: Some(IType::Record(0)),
        error_handler: None,
    };

    let config = AquamarineVMConfig {
        call_service,
        aquamarine_wasm_path: aquamarine_fname(None),
        current_peer_id: LOCAL_VM.to_string(),
        particle_data_store: format!("/tmp/{}", uuid()).into(),
        logging_mask: i64::max_value(),
    };

    let vm = AquamarineVM::new(config).expect("vm should be created");

    vm
}

fn route(args: Vec<IValue>, load: LoadMap, ret: ReturnMap) -> Option<IValue> {
    let args = Args::parse(args).expect("valid args");
    match args.service_id.as_str() {
        "load" => load
            .lock()
            .get(args.fname.as_str())
            .map(|v| ivalue_utils::ok(v.clone()))
            .unwrap_or(ivalue_utils::error(JValue::String(f!(
                "variable not found: {args.fname}"
            )))),
        "return" => {
            log::warn!("return args {:?}", args.args);
            let mut ret = ret.lock();
            if let Some(vec) = ret.get_mut(args.fname.as_str()) {
                vec.extend(args.args)
            } else {
                ret.insert(args.fname, args.args);
            }
            ivalue_utils::unit()
        }
        "identity" => ivalue_utils::ok(JValue::Array(args.args)),
        service => ivalue_utils::error(JValue::String(f!("service not found: {service}"))),
    }
}

pub fn make_particle(
    peer_id: PeerId,
    data: HashMap<&'static str, JValue>,
    script: String,
) -> Particle {
    VM.with(|vm| vm.borrow_mut().make_particle(data, script, peer_id))
}

pub fn read_args(particle: Particle, peer_id: &PeerId, key: &str) -> Option<Vec<JValue>> {
    VM.with(|vm| vm.borrow_mut().read_args(particle, peer_id, key))
}
