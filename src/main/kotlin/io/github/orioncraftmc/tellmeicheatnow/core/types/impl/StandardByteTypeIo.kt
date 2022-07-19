package io.github.orioncraftmc.tellmeicheatnow.core.types.impl

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StandardByteTypeIo : ProtocolTypeIo<Int> {
    override fun read(dis: DataInputStream): Int {
        return dis.read()
    }

    override fun write(dos: DataOutputStream, value: Int) {
        dos.write(value)
    }
}