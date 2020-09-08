use std::collections::HashMap;

// TODO: it should be InterfaceType value
type Value = serde_json::Value;

enum Peer {
    Current,
    // TODO: what's value?
    ArgPeer { idx: u64 },
    PeerByPk(String),
    // TODO: call it `Terminate`?
    Null,
}

enum Op {
    Call,
    Match,
    Mismatch,
}

enum Prolog {
    Free,
    Body(Op),
}

// TODO: how to describe type of Arg?
struct Arg {
    idx: u64,
    // TODO: add json_path field here
}

enum KnownCall {
    Sent { calledBy: String },
    Executed { calledBy: String, output: Value },
}

struct Init {
    input: Vec<Value>,
}

struct ExecutionState {
    calls: HashMap<String, KnownCall>,
}
