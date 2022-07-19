package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition

data class TmicnProtocolDefinitionGenerationModel(
    val definition: ProtocolDefinition,
) {
    val packets: List<TmicnPacketGenerationModel> = definition.packets.map { TmicnPacketGenerationModel(this, it) }
}