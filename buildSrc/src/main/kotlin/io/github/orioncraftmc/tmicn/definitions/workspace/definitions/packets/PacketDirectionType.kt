package io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets

enum class PacketDirectionType(val friendlyName: String, val fromDefinition: String) {
    Unknown("Unknown", "unknown"),
    ClientToServer("Client to Server", "client-to-server"),
    ServerToClient("Server to Client", "server-to-client");

    companion object {
        fun fromDefinition(definition: String): PacketDirectionType {
            return values().firstOrNull { it.fromDefinition == definition } ?: Unknown
        }
    }
}