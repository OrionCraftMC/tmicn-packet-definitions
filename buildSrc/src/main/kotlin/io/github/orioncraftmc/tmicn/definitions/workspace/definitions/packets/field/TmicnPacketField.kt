package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field

import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.Documentable

data class TmicnPacketField(
    override val name: String,
    override val documentation: String,
    var type: String = ""
) : Documentable