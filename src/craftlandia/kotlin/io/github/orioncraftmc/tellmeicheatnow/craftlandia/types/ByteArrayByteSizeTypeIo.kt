package io.github.orioncraftmc.tellmeicheatnow.craftlandia.types

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object ByteArrayByteSizeTypeIo : ProtocolTypeIo<ByteArray> {
    override fun read(dis: DataInputStream): ByteArray {
        val length = dis.read()
        val bytes = ByteArray(length)
        dis.read(bytes)

        return bytes
    }

    override fun write(dos: DataOutputStream, value: ByteArray) {
        dos.write(value.size)
        dos.write(value)
    }

}