package io.github.orioncraftmc.tellmeicheatnow.core.types

import java.io.DataInputStream

interface ProtocolType<T> {

    fun read(dis: DataInputStream): T

    fun write(dis: DataInputStream, value: T)

}