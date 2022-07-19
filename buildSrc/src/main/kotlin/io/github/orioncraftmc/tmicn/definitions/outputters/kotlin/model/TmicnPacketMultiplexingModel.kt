package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.CodeBlock
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacketMultiplexingData

data class TmicnPacketMultiplexingModel(
    val definition: TmicnProtocolDefinitionGenerationModel,
    val packet: TmicnPacketGenerationModel,
    val multiplexingData: TmicnPacketMultiplexingData? = packet.packet.multiplexing,
    val isMultiplexingPackets: Boolean = multiplexingData != null,
    val multiplexIdField: TmicnPacketFieldGenerationModel? =
        packet.fields.firstOrNull { it.field.name == WorkspaceConstants.MultiplexConstants.MULTIPLEX_ID_FIELD_NAME },
    val multiplexContentField: TmicnPacketFieldGenerationModel? =
        packet.fields.firstOrNull { it.field.type == WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE },

    ) {
    val multiplexedPackets = mutableMapOf<Long, TmicnPacketGenerationModel>()

    val asReadPacket by lazy {
        val builder = CodeBlock.builder()
        require(multiplexIdField != null) { "Multiplexing packets must have a multiplex id field named ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_ID_FIELD_NAME}" }
        require(multiplexContentField != null) { "Multiplexing packets must have a multiplex content field with type ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE}" }

        builder.beginControlFlow(
            "when (%N)",
            multiplexIdField.fieldName
        )

        for ((id, multiplexedPacket) in packet.multiplexingModel.multiplexedPackets) {
            builder.addStatement("%L -> %L", id, multiplexedPacket.getAsIoRead(packet.asPacketIoModel.inputStreamParameter))
        }
        builder.addStatement(
            "else -> throw %T(%P)",
            IllegalStateException::class,
            "Unknown multiplex id \$multiplexId"
        )
        builder.endControlFlow()

        builder.build()
    }

    val asWritePacketContent by lazy {
        val builder = CodeBlock.builder()
        require(multiplexIdField != null) { "Multiplexing packets must have a multiplex id field named ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_ID_FIELD_NAME}" }
        require(multiplexContentField != null) { "Multiplexing packets must have a multiplex content field with type ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE}" }

        builder.beginControlFlow(
            "when (%L)",
            outputPacketContent
        )

        for ((id, multiplexedPacket) in packet.multiplexingModel.multiplexedPackets) {
            builder.add("is %T -> %L\n", multiplexedPacket.asClassName, multiplexedPacket.getAsIoWrite(
                packet.asPacketIoModel.outputStreamParameter,
                outputPacketContent,
                multiplexedPacket.asClassName
            ))
        }
        builder.add(
            "else -> throw %T(%S)\n",
            IllegalStateException::class,
            "Unknown packet type"
        )
        builder.endControlFlow()

        builder.build()
    }

    val outputPacketContent by lazy {
        require(multiplexContentField != null) { "Multiplexing packets must have a multiplex content field with type ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE}" }
        CodeBlock.of(
            "%N.%N",
            packet.asPacketIoModel.outputValueParameter,
            multiplexContentField.fieldName
        )
    }

    fun getAsWritePacketId(): CodeBlock {
        val builder = CodeBlock.builder()
        require(multiplexIdField != null) { "Multiplexing packets must have a multiplex id field named ${WorkspaceConstants.MultiplexConstants.MULTIPLEX_ID_FIELD_NAME}" }

        builder.beginControlFlow("when (%L)", outputPacketContent)

        for ((id, multiplexedPacket) in packet.multiplexingModel.multiplexedPackets) {
            builder.add("is %T -> %L\n", multiplexedPacket.asClassName, id)
        }
        builder.add(
            "else -> throw %T(%P)\n",
            IllegalStateException::class,
            CodeBlock.of("Unknown packet type")
        )
        builder.endControlFlow()

        return builder.build()
    }


    init {
        if (multiplexingData != null) {
            val startId = multiplexingData.startId
            for ((index, packetId) in (startId until startId + multiplexingData.packets.size).withIndex()) {
                multiplexedPackets[packetId] =
                    definition.packets.first { it.packet.name == multiplexingData.packets[index] }
            }
        }
    }
}