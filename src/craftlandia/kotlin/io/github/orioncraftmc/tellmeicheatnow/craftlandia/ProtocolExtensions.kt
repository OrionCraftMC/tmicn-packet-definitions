package io.github.orioncraftmc.tellmeicheatnow.craftlandia

import io.github.orioncraftmc.tellmeicheatnow.core.packets.TmicnPacket
import io.github.orioncraftmc.tellmeicheatnow.core.packets.metadata.annotations.PacketMultiplexedByMetadata
import io.github.orioncraftmc.tellmeicheatnow.craftlandia.constants.CraftLandiaAntiCheatConstants
import io.github.orioncraftmc.tellmeicheatnow.protocols.craftlandia.packets.AntihackVersionPacket
import io.github.orioncraftmc.tellmeicheatnow.protocols.craftlandia.packets.antihack.AntihackPayloadPacket
import io.github.orioncraftmc.tellmeicheatnow.protocols.craftlandia.packets.antihack.clientdata.ClientDataResultPacket
import io.github.orioncraftmc.tellmeicheatnow.protocols.craftlandia.packets.antihack.gamehash.GameHashRequestPacket
import java.util.*

fun CraftLandiaAntiCheatConstants.createAntihackVersion(): AntihackVersionPacket {
    return AntihackVersionPacket(this.antiHackVersion)
}

fun createGameHashRequest(): GameHashRequestPacket {
    return GameHashRequestPacket(UUID.randomUUID().toString().substringBefore('-'))
}

fun CraftLandiaAntiCheatConstants.createGameHashResult(request: GameHashRequestPacket): String {
    return "${request.chatPrefix}${this.antiHackReplyId}/${this.clientJarHash}"
}

fun CraftLandiaAntiCheatConstants.createClientDataResult(): ClientDataResultPacket {
    return ClientDataResultPacket(
        this.clientData.speedModifier,
        this.clientData.unknownData1,
        this.clientData.unknownData2,
        this.clientData.unknownData3,
        this.clientData.unknownData4,
        this.clientData.unknownData5,
        this.clientData.entityHitboxWidth,
        this.clientData.entityHitboxHeight,
    )
}

fun <T: TmicnPacket> T.wrapPacketIfNeeded(): TmicnPacket {
    return if (javaClass.isAnnotationPresent(PacketMultiplexedByMetadata::class.java)) {
        AntihackPayloadPacket(UUID.randomUUID().toString(), this)
    } else {
        this
    }
}