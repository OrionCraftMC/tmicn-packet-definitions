package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets

import com.fasterxml.jackson.annotation.JsonProperty

data class TmicnPacketMultiplexingData(
    @JsonProperty("start_id")
    val startId: Long,
    val packets: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TmicnPacketMultiplexingData

        if (startId != other.startId) return false
        if (!packets.contentEquals(other.packets)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startId.hashCode()
        result = 31 * result + packets.contentHashCode()
        return result
    }
}