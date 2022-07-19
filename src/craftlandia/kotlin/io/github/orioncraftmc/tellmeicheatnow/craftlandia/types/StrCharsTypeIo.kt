package io.github.orioncraftmc.tellmeicheatnow.craftlandia.types

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StrCharsTypeIo : ProtocolTypeIo<String> {
    override fun read(dis: DataInputStream): String {
        val length = dis.readShort()
        require(length >= 0) { "Length must be positive, but was $length" }
        require(length <= Short.MAX_VALUE) { "Length must be less than ${Short.MAX_VALUE + 1}, but was $length" }
        val chars = CharArray(length.toInt())
        for (i in 0 until length) {
            chars[i] = dis.readChar()
        }
        return String(chars)
    }

    override fun write(dos: DataOutputStream, value: String) {
        require(value.length <= Short.MAX_VALUE) { "Length must be less than ${Short.MAX_VALUE + 1}, but was ${value.length}" }
        dos.writeShort(value.length)
        dos.writeChars(value)
    }
}