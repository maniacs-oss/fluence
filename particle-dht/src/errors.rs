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

use libp2p::kad::record::Key;
use libp2p::kad::store;

#[derive(Debug)]
pub enum PublishError {
    StoreError(store::Error),
    TimedOut,
    QuorumFailed,
}

#[derive(Debug, Clone)]
pub struct ResolveError {
    pub key: Key,
    pub kind: ResolveErrorKind,
}

#[derive(Debug, Clone)]
pub enum ResolveErrorKind {
    TimedOut,
    QuorumFailed,
    NotFound,
}