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

use crate::file_names::extract_module_name;
use crate::{file_names, files};

use host_closure::{closure, closure_opt, Args, Closure};
use json_utils::as_value;

use serde_json::Value;
use std::path::PathBuf;

/// Adds a module to the filesystem, overwriting existing module.
pub fn add_module(modules_dir: PathBuf) -> Closure {
    closure_opt(move |mut args| {
        log::debug!("add_module called");
        let module = Args::next("module", &mut args).map_err(as_value)?;
        let config = Args::next("config", &mut args).map_err(as_value)?;
        log::debug!("add_module parsed");
        files::add_module(&modules_dir, module, config).map_err(as_value)?;
        log::debug!("add_module finished");

        Ok(None)
    })
}

/// Saves new blueprint to disk
pub fn add_blueprint(blueprint_dir: PathBuf) -> Closure {
    closure(move |mut args| {
        let blueprint = Args::next("blueprint", &mut args).map_err(as_value)?;
        files::add_blueprint(&blueprint_dir, &blueprint).map_err(as_value)?;

        Ok(Value::String(blueprint.id))
    })
}

/// Get available modules (intersection of modules from config + modules on filesystem)
// TODO: load interfaces of these modules
pub fn get_modules(modules_dir: PathBuf) -> Closure {
    closure(move |_| {
        Ok(Value::Array(
            files::list_files(&modules_dir)
                .into_iter()
                .flatten()
                .filter_map(|pb| extract_module_name(pb.file_name()?.to_str()?).map(Value::String))
                .collect(),
        ))
    })
}

/// Get available blueprints
pub fn get_blueprints(blueprint_dir: PathBuf) -> Closure {
    closure(move |_| {
        Ok(Value::Array(
            files::list_files(&blueprint_dir)
                .into_iter()
                .flatten()
                .filter_map(|pb| {
                    // Check if file name matches blueprint schema
                    pb.file_name()?
                        .to_str()
                        .filter(|s| file_names::is_blueprint(s))?;

                    // Read & deserialize TOML
                    let bytes = std::fs::read(pb).ok()?;
                    let config = toml::from_slice(bytes.as_slice()).ok()?;

                    // Convert to json
                    serde_json::to_value(config).ok()
                })
                .collect(),
        ))
    })
}