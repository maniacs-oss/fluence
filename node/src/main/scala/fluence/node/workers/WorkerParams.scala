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

package fluence.node.workers
import fluence.node.docker.DockerParams
import fluence.node.eth.WorkerNode
import scodec.bits.ByteVector

/**
 * Worker container's params
 */
case class WorkerParams(
  appId: ByteVector,
  currentWorker: WorkerNode,
  workerPath: String,
  vmCodePath: String,
  masterNodeContainerId: Option[String],
  image: WorkerImage
) {

  // Convert bytes32 to hex for better human-readability. As of current appId generation in Fluence Contract,
  // there's a lot of leading zeros in app IDs, so skip them to avoid visual clutter.
  val appIdHex: String = appId.toHex

  override def toString =
    s"(worker ${currentWorker.index} with RPC port ${currentWorker.rpcPort} for app $appIdHex)"

  /**
   * [[fluence.node.docker.DockerIO.exec]]'s command for launching a configured worker
   */
  val dockerCommand: DockerParams.DaemonParams = {
    val params = DockerParams
      .build()
      .option("-e", s"""CODE_DIR=$vmCodePath""")
      .option("-e", s"""WORKER_DIR=$workerPath""")
      .option("--name", s"${appIdHex}_worker_${currentWorker.index}")
      .port(currentWorker.p2pPort, 26656)
      .port(currentWorker.rpcPort, 26657)
      .port(currentWorker.tmPrometheusPort, 26660)
      .port(currentWorker.smPrometheusPort, 26661)

    (masterNodeContainerId match {
      case Some(id) => params.option("--volumes-from", s"$id:ro")
      case None => params
    }).image(image.imageName).daemonRun()
  }
}
