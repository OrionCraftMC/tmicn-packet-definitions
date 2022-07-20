package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets

enum class PacketDirectionType(val friendlyName: String, val fromDefinition: String) {
    UNKNOWN("Unknown", "unknown"),
    CLIENT_TO_SERVER("Client to Server", "client-to-server"),
    SERVER_TO_CLIENT("Server to Client", "server-to-client");

    companion object {
        fun fromDefinition(definition: String): PacketDirectionType {
            return values().firstOrNull { it.fromDefinition == definition } ?: UNKNOWN
        }
    }
}