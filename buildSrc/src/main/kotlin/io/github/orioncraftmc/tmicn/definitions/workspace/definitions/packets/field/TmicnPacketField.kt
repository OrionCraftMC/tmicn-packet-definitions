package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.Documentable

data class TmicnPacketField(
    override val name: String,
    override val documentation: String
) : Documentable {

    @JsonIgnore
    var type: TmicnPacketFieldType = TmicnPacketFieldType.UNKNOWN

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    internal var typeStr: String
        get() = type.fromDefinition
        set(value) {
            type = TmicnPacketFieldType.fromDefinition(value)
        }
}
