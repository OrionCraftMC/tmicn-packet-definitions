package io.github.orioncraftmc.tellmeicheatnow.core.types.impl

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StandardUtfTypeIo : ProtocolTypeIo<String> {
    override fun read(dis: DataInputStream): String {
        return dis.readUTF()
    }

    override fun write(dos: DataOutputStream, value: String) {
        dos.writeUTF(value)
    }
}