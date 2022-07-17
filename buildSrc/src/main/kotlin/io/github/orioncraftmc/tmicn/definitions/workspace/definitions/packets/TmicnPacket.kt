package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.Documentable
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField

data class TmicnPacket(
    override val name: String,
    override val documentation: String,
    @JsonProperty("plugin-message-channel")
    val pluginMessageChannel: String?,
) : Documentable {

    val fields: MutableList<TmicnPacketField> = mutableListOf()

    @JsonIgnore
    var direction: PacketDirectionType = PacketDirectionType.Unknown

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    internal var directionStr: String
        get() = direction.fromDefinition
        set(value) {
            direction = PacketDirectionType.fromDefinition(value)
        }

}