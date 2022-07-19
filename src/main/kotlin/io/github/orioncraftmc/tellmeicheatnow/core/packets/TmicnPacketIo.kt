package io.github.orioncraftmc.tellmeicheatnow.core.packets

import java.io.DataOutputStream

interface TmicnPacketIo<T : TmicnPacket> {

    fun write(dos: DataOutputStream)

}