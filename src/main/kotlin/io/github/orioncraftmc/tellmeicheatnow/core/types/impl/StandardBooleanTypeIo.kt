package io.github.orioncraftmc.tellmeicheatnow.core.types.impl

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StandardBooleanTypeIo : ProtocolTypeIo<Boolean> {
    override fun read(dis: DataInputStream): Boolean {
        return dis.readBoolean()
    }

    override fun write(dos: DataOutputStream, value: Boolean) {
        dos.writeBoolean(value)
    }
}


