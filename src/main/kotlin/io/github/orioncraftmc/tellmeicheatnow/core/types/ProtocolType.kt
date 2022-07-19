package io.github.orioncraftmc.tellmeicheatnow.core.types

import java.io.DataInputStream
import java.io.DataOutputStream

interface ProtocolType<T> {

    fun read(dis: DataInputStream): T

    fun write(dos: DataOutputStream, value: T)

}