package io.github.orioncraftmc.tellmeicheatnow.craftlandia.types

import io.github.orioncraftmc.tellmeicheatnow.core.types.ProtocolTypeIo
import java.io.DataInputStream
import java.io.DataOutputStream

object StrBytesTypeIo : ProtocolTypeIo<String> {
    override fun read(dis: DataInputStream): String {
        var result = ""

        // First check if we have a string
        val hasValue = dis.read() == 1
        if (hasValue) {
            // We do, so read the number of bytes that make up the string
            val length = dis.read()
            val bytes = ByteArray(length)
            // Read the bytes from the stream into the array
            dis.read(bytes)

            result = String(bytes)
        }

        return result
    }

    override fun write(dos: DataOutputStream, value: String) {
        val bytes = value.encodeToByteArray()

        // Whether we have a string
        val notEmpty = value.isNotEmpty()
        dos.write(if (notEmpty) 1 else 0)

        if (!notEmpty) return

        // Amount of bytes to write
        dos.write(bytes.size)
        // The bytes
        dos.write(bytes)
    }

}