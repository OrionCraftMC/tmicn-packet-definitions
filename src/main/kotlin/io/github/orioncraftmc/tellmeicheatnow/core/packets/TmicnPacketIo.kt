package io.github.orioncraftmc.tellmeicheatnow.core.packets

import java.io.DataInputStream
import java.io.DataOutputStream

interface TmicnPacketIo<T : TmicnPacket> {

    fun write(dos: DataOutputStream, value: T)

    fun read(dis: DataInputStream): T

}