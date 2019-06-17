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

// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package proto3.tendermint

/** === Canonical ===
 *
 * @param type
 *   it's actually byte in Go, be careful
 */
@SerialVersionUID(0L)
final case class CanonicalVote(
  `type`: _root_.scala.Int = 0,
  height: _root_.scala.Long = 0L,
  round: _root_.scala.Long = 0L,
  blockId: _root_.scala.Option[proto3.tendermint.CanonicalBlockID] = None,
  time: _root_.scala.Option[com.google.protobuf.timestamp.Timestamp] = None,
  chainId: _root_.scala.Predef.String = ""
) extends scalapb.GeneratedMessage with scalapb.Message[CanonicalVote] with scalapb.lenses.Updatable[CanonicalVote] {

  @transient
  private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
  private[this] def __computeSerializedValue(): _root_.scala.Int = {
    var __size = 0

    {
      val __value = `type`
      if (__value != 0) {
        __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(1, __value)
      }
    };

    {
      val __value = height
      if (__value != 0L) {
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFixed64Size(2, __value)
      }
    };

    {
      val __value = round
      if (__value != 0L) {
        __size += _root_.com.google.protobuf.CodedOutputStream.computeFixed64Size(3, __value)
      }
    };
    if (blockId.isDefined) {
      val __value = blockId.get
      __size += 1 + _root_.com.google.protobuf.CodedOutputStream
        .computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
    };
    if (time.isDefined) {
      val __value = time.get
      __size += 1 + _root_.com.google.protobuf.CodedOutputStream
        .computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
    };

    {
      val __value = chainId
      if (__value != "") {
        __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(6, __value)
      }
    };
    __size
  }
  final override def serializedSize: _root_.scala.Int = {
    var read = __serializedSizeCachedValue
    if (read == 0) {
      read = __computeSerializedValue()
      __serializedSizeCachedValue = read
    }
    read
  }

  def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
    {
      val __v = `type`
      if (__v != 0) {
        _output__.writeInt32(1, __v)
      }
    };
    {
      val __v = height
      if (__v != 0L) {
        _output__.writeFixed64(2, __v)
      }
    };
    {
      val __v = round
      if (__v != 0L) {
        _output__.writeFixed64(3, __v)
      }
    };
    blockId.foreach { __v =>
      val __m = __v
      _output__.writeTag(4, 2)
      _output__.writeUInt32NoTag(__m.serializedSize)
      __m.writeTo(_output__)
    };
    time.foreach { __v =>
      val __m = __v
      _output__.writeTag(5, 2)
      _output__.writeUInt32NoTag(__m.serializedSize)
      __m.writeTo(_output__)
    };
    {
      val __v = chainId
      if (__v != "") {
        _output__.writeString(6, __v)
      }
    };
  }

  def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): proto3.tendermint.CanonicalVote = {
    var __type = this.`type`
    var __height = this.height
    var __round = this.round
    var __blockId = this.blockId
    var __time = this.time
    var __chainId = this.chainId
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 8 =>
          __type = _input__.readInt32()
        case 17 =>
          __height = _input__.readFixed64()
        case 25 =>
          __round = _input__.readFixed64()
        case 34 =>
          __blockId = Option(
            _root_.scalapb.LiteParser
              .readMessage(_input__, __blockId.getOrElse(proto3.tendermint.CanonicalBlockID.defaultInstance))
          )
        case 42 =>
          __time = Option(
            _root_.scalapb.LiteParser
              .readMessage(_input__, __time.getOrElse(com.google.protobuf.timestamp.Timestamp.defaultInstance))
          )
        case 50 =>
          __chainId = _input__.readString()
        case tag => _input__.skipField(tag)
      }
    }
    proto3.tendermint.CanonicalVote(
      `type` = __type,
      height = __height,
      round = __round,
      blockId = __blockId,
      time = __time,
      chainId = __chainId
    )
  }
  def withType(__v: _root_.scala.Int): CanonicalVote = copy(`type` = __v)
  def withHeight(__v: _root_.scala.Long): CanonicalVote = copy(height = __v)
  def withRound(__v: _root_.scala.Long): CanonicalVote = copy(round = __v)

  def getBlockId: proto3.tendermint.CanonicalBlockID =
    blockId.getOrElse(proto3.tendermint.CanonicalBlockID.defaultInstance)
  def clearBlockId: CanonicalVote = copy(blockId = None)
  def withBlockId(__v: proto3.tendermint.CanonicalBlockID): CanonicalVote = copy(blockId = Option(__v))

  def getTime: com.google.protobuf.timestamp.Timestamp =
    time.getOrElse(com.google.protobuf.timestamp.Timestamp.defaultInstance)
  def clearTime: CanonicalVote = copy(time = None)
  def withTime(__v: com.google.protobuf.timestamp.Timestamp): CanonicalVote = copy(time = Option(__v))
  def withChainId(__v: _root_.scala.Predef.String): CanonicalVote = copy(chainId = __v)

  def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
    (__fieldNumber: @ _root_.scala.unchecked) match {
      case 1 => {
        val __t = `type`
        if (__t != 0) __t else null
      }
      case 2 => {
        val __t = height
        if (__t != 0L) __t else null
      }
      case 3 => {
        val __t = round
        if (__t != 0L) __t else null
      }
      case 4 => blockId.orNull
      case 5 => time.orNull
      case 6 => {
        val __t = chainId
        if (__t != "") __t else null
      }
    }
  }

  def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
    _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
    (__field.number: @ _root_.scala.unchecked) match {
      case 1 => _root_.scalapb.descriptors.PInt(`type`)
      case 2 => _root_.scalapb.descriptors.PLong(height)
      case 3 => _root_.scalapb.descriptors.PLong(round)
      case 4 => blockId.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      case 5 => time.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      case 6 => _root_.scalapb.descriptors.PString(chainId)
    }
  }
  def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
  def companion = proto3.tendermint.CanonicalVote
}

object CanonicalVote extends scalapb.GeneratedMessageCompanion[proto3.tendermint.CanonicalVote] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[proto3.tendermint.CanonicalVote] = this

  def fromFieldsMap(
    __fieldsMap: scala.collection.immutable.Map[
      _root_.com.google.protobuf.Descriptors.FieldDescriptor,
      _root_.scala.Any
    ]
  ): proto3.tendermint.CanonicalVote = {
    _root_.scala.Predef.require(
      __fieldsMap.keys.forall(_.getContainingType() == javaDescriptor),
      "FieldDescriptor does not match message type."
    )
    val __fields = javaDescriptor.getFields
    proto3.tendermint.CanonicalVote(
      __fieldsMap.getOrElse(__fields.get(0), 0).asInstanceOf[_root_.scala.Int],
      __fieldsMap.getOrElse(__fields.get(1), 0L).asInstanceOf[_root_.scala.Long],
      __fieldsMap.getOrElse(__fields.get(2), 0L).asInstanceOf[_root_.scala.Long],
      __fieldsMap.get(__fields.get(3)).asInstanceOf[_root_.scala.Option[proto3.tendermint.CanonicalBlockID]],
      __fieldsMap.get(__fields.get(4)).asInstanceOf[_root_.scala.Option[com.google.protobuf.timestamp.Timestamp]],
      __fieldsMap.getOrElse(__fields.get(5), "").asInstanceOf[_root_.scala.Predef.String]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[proto3.tendermint.CanonicalVote] =
    _root_.scalapb.descriptors.Reads {
      case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
        _root_.scala.Predef.require(
          __fieldsMap.keys.forall(_.containingMessage == scalaDescriptor),
          "FieldDescriptor does not match message type."
        )
        proto3.tendermint.CanonicalVote(
          __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Int]).getOrElse(0),
          __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Long]).getOrElse(0L),
          __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).map(_.as[_root_.scala.Long]).getOrElse(0L),
          __fieldsMap
            .get(scalaDescriptor.findFieldByNumber(4).get)
            .flatMap(_.as[_root_.scala.Option[proto3.tendermint.CanonicalBlockID]]),
          __fieldsMap
            .get(scalaDescriptor.findFieldByNumber(5).get)
            .flatMap(_.as[_root_.scala.Option[com.google.protobuf.timestamp.Timestamp]]),
          __fieldsMap.get(scalaDescriptor.findFieldByNumber(6).get).map(_.as[_root_.scala.Predef.String]).getOrElse("")
        )
      case _ => throw new RuntimeException("Expected PMessage")
    }

  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor =
    TendermintProto.javaDescriptor.getMessageTypes.get(11)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = TendermintProto.scalaDescriptor.messages(11)

  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @ _root_.scala.unchecked) match {
      case 4 => __out = proto3.tendermint.CanonicalBlockID
      case 5 => __out = com.google.protobuf.timestamp.Timestamp
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty

  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] =
    throw new MatchError(__fieldNumber)
  lazy val defaultInstance = proto3.tendermint.CanonicalVote(
    )
  implicit class CanonicalVoteLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, proto3.tendermint.CanonicalVote])
      extends _root_.scalapb.lenses.ObjectLens[UpperPB, proto3.tendermint.CanonicalVote](_l) {

    def `type`: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] =
      field(_.`type`)((c_, f_) => c_.copy(`type` = f_))

    def height: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] =
      field(_.height)((c_, f_) => c_.copy(height = f_))
    def round: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.round)((c_, f_) => c_.copy(round = f_))

    def blockId: _root_.scalapb.lenses.Lens[UpperPB, proto3.tendermint.CanonicalBlockID] =
      field(_.getBlockId)((c_, f_) => c_.copy(blockId = Option(f_)))

    def optionalBlockId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[proto3.tendermint.CanonicalBlockID]] =
      field(_.blockId)((c_, f_) => c_.copy(blockId = f_))

    def time: _root_.scalapb.lenses.Lens[UpperPB, com.google.protobuf.timestamp.Timestamp] =
      field(_.getTime)((c_, f_) => c_.copy(time = Option(f_)))

    def optionalTime
      : _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Option[com.google.protobuf.timestamp.Timestamp]] =
      field(_.time)((c_, f_) => c_.copy(time = f_))

    def chainId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] =
      field(_.chainId)((c_, f_) => c_.copy(chainId = f_))
  }
  final val TYPE_FIELD_NUMBER = 1
  final val HEIGHT_FIELD_NUMBER = 2
  final val ROUND_FIELD_NUMBER = 3
  final val BLOCK_ID_FIELD_NUMBER = 4
  final val TIME_FIELD_NUMBER = 5
  final val CHAIN_ID_FIELD_NUMBER = 6
}