package io.github.orioncraftmc.tellmeicheatnow.craftlandia.types

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StrCharsArrayIntSizeTypeIo : ProtocolTypeIo<Array<String>> {
    override fun read(dis: DataInputStream): Array<String> {
        val size = dis.readInt()
        val array = Array(size) { "" }
        for (i in 0 until size) {
            array[i] = StrCharsTypeIo.read(dis)
        }

        return array
    }

    override fun write(dos: DataOutputStream, value: Array<String>) {
        dos.writeInt(value.size)
        for (element in value) {
            StrCharsTypeIo.write(dos, element)
        }
    }

}