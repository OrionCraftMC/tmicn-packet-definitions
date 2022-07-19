package io.github.orioncraftmc.tellmeicheatnow.core.packets

import java.io.DataInputStream

interface TmicnPacketIo<T : TmicnPacket> {

   // fun write(dos: DataOutputStream)

    fun read(dis: DataInputStream): T

}