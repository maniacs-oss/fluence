use libp2p_core::identity::ed25519::PublicKey;
use libp2p_core::PeerId;
use std::collections::HashMap;

// TODO: it should be InterfaceType value
type Value = serde_json::Value;
type Selector = jsonpath_lib::Selector<'static, 'static>;
type Input = HashMap<u32, Value>;

enum Peer {
    Current,
    // TODO: what's value?
    ArgPeer { idx: u32 },
    PeerByPk(PublicKey),
}

enum Op {
    Call {
        peer: Peer,
        fname: String,
        args: Vec<Arg>,
    },
    Match,
    Mismatch,
    Null,
}

// TODO: how to describe type of Arg?
struct Arg {
    idx: u32,
    selector: Selector,
}

enum KnownCall {
    Sent { called_by: String },
    Executed { called_by: String, output: Value },
}

struct Init {
    input: Vec<Value>,
}

// TODO: rename to Particle?
struct ExecutionState {
    calls: HashMap<String, KnownCall>,
    init: Init,
    free_vars: u32,
    script: Op,
}

/// Called 'Temp' in Elm
struct StepState {
    current_pk: PublicKey,
    /// reversed stacktrace of operations
    trace: Vec<Op>,
    /// currently executing operation
    current_op: Op,
    /// Dict of (position -> output). Position is the length of the `path` the output was produced at
    input: Input,
    /// destinations for the final result
    send_to: Vec<PeerId>,
}

fn resolve_args(input: &Input, args: &[Arg]) -> Option<Vec<Value>> {
    unimplemented!("resolve_args")
}

fn parse_pubkey(value: &Value) -> Option<PublicKey> {}

fn resolve_pubkey(step: &StepState, peer: &Peer) -> Option<&PublicKey> {
    match peer {
        Peer::Current => Some(&step.current_pk),
        Peer::ArgPeer { idx } => step.input.get(&idx).and_then(parse_pubkey),
        Peer::PeerByPk(pk) => Some(pk),
    }
}

fn function_hash(peer: &Peer, fname: &String, args: &[Value]) -> String {
    unimplemented!("function_hash")
}

fn switch_branch(step: StepState, execution: ExecutionState) -> (ExecutionState, Vec<PeerId>) {
    unimplemented!("switch_branch")
}

fn advance(step: StepState, execution: ExecutionState) -> (ExecutionState, Vec<PeerId>) {
    match &step.current_op {
        Op::Call { peer, fname, args } => {
            let resolve = || {
                let args = resolve_args(&step.input, args)?;
                let fhash = function_hash(&peer, &fname, &args);
                let peer = resolve_pubkey(&step, peer)?;

                (args, fhash, peer)
            };

            if let Some((args, peer, fhash)) = resolve() {
                if let Some(call) = execution.calls.get(fhash) {
                    unimplemented!()
                } else {
                    unimplemented!()
                }
            } else {
                switch_branch(step, execution)
            }
        }
        Op::Match => {}
        Op::Mismatch => {}
    }
}
