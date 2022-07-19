package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacketMultiplexingData

data class TmicnPacketMultiplexingModel(
    val definition: TmicnProtocolDefinitionGenerationModel,
    val multiplexingData: TmicnPacketMultiplexingData?,
    val isMultiplexingPackets: Boolean = multiplexingData != null
) {
    val multiplexedPackets = mutableMapOf<Long, TmicnPacketGenerationModel>()

    init {
        if (multiplexingData != null) {
            val startId = multiplexingData.startId
            for ((index, packetId) in (startId until startId + multiplexingData.packets.size).withIndex()) {
                multiplexedPackets[packetId] = definition.packets.first { it.packet.name == multiplexingData.packets[index] }
            }
        }
    }
}