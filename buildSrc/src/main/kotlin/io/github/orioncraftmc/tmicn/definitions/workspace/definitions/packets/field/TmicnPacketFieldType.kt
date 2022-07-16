package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field

enum class TmicnPacketFieldType(val fromDefinition: String, val documentation: String) {
    UNKNOWN("unknown", "Unknown"),
    BYTE("byte", "Byte read with `InputStream#read()`."),
    UTF("utf", "UTF-8 string read with `DataInputStream#readUTF()`."),
    BOOLEAN("boolean", "Boolean read with `DataInputStream#readBoolean()`."),
    FLOAT("float", "float read with `DataInputStream#readFloat()`."),
    UTF_BYTES(
        "utf_bytes",
        """UTF-8 string read from a byte array.
        |
        |Steps:
        |1. Read whether the string is available or not.
        |2. If it is, read the legth of the string.
        |3. Read the string.""".trimMargin()
    );

    companion object {
        fun fromDefinition(definition: String): TmicnPacketFieldType {
            return values().firstOrNull { it.fromDefinition == definition } ?: UNKNOWN
        }
    }
}