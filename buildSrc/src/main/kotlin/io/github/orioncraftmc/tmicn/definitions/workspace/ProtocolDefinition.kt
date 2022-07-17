package io.github.orioncraftmc.tmicn.definitions.workspace

import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocol
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket

data class ProtocolDefinition(
    val name: String,
    val protocol: TmicnProtocol,
    val packets: List<TmicnPacket>
) {

}