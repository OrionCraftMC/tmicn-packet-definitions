package io.github.orioncraftmc.tellmeicheatnow.core.packets.metadata.annotations

import io.github.orioncraftmc.tellmeicheatnow.core.packets.TmicnPacket
import kotlin.reflect.KClass

annotation class PacketMultiplexedByMetadata(val packet: KClass<out TmicnPacket>)
