/*
 * Copyright 2018 Fluence Labs Limited
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

package fluence.effects.tendermint.block.data

import io.circe.generic.extras.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import proto3.tendermint.{BlockID, Vote}

/**
 * Commits for a previous Block
 *
 * @param block_id Previous block's id (hashes)
 * @param precommits List of commits (votes)
 */
case class LastCommit(block_id: BlockID, precommits: List[Option[Vote]] = List.empty)

object LastCommit {
  import JsonCodecs.{blockIdDecoder, conf, messageEncoder, voteDecoder}

  implicit final val lastCommitDecoder: Decoder[LastCommit] = deriveDecoder
  implicit final val lastCommitEncoder: Encoder[LastCommit] = deriveEncoder
}