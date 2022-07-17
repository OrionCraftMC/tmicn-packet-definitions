package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.Documentable
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField
import java.nio.file.Path
import java.util.*

data class TmicnPacket(
    override val name: String,
    override val documentation: String,
    @JsonProperty("plugin-message-channel")
    val pluginMessageChannel: String?,
    val multiplexing: TmicnPacketMultiplexingData? = null
) : Documentable {

    @JsonIgnore
    lateinit var path: Path

    val fields: MutableList<TmicnPacketField> = mutableListOf()

    @JsonIgnore
    var directions: EnumSet<PacketDirectionType> = EnumSet.of(PacketDirectionType.Unknown)

    @get:JsonProperty("type")
    @set:JsonProperty("type")
    internal var directionStr: Array<String>
        get() = directions.map { it.fromDefinition }.toTypedArray()
        set(value) {
            directions = EnumSet.copyOf(value.map { PacketDirectionType.fromDefinition(it) })
        }

}