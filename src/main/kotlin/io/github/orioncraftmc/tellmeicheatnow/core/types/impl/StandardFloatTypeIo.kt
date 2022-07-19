package io.github.orioncraftmc.tellmeicheatnow.core.types.impl

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StandardFloatTypeIo : ProtocolTypeIo<Float> {
    override fun read(dis: DataInputStream): Float {
        return dis.readFloat()
    }

    override fun write(dos: DataOutputStream, value: Float) {
        dos.writeFloat(value)
    }
}