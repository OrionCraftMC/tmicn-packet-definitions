package io.github.orioncraftmc.tellmeicheatnow.core.types.impl

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StandardDoubleTypeIo : ProtocolTypeIo<Double> {
    override fun read(dis: DataInputStream): Double {
        return dis.readDouble()
    }

    override fun write(dos: DataOutputStream, value: Double) {
        dos.writeDouble(value)
    }
}