package io.github.orioncraftmc.tmicn.definitions.workspace

import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocol
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket

data class ProtocolDefinition(
    val name: String,
    val protocol: TmicnProtocol,
    val packets: List<TmicnPacket>
) {

    fun renderToMarkdown() = buildString {

        appendLine("# ${protocol.name} (`$name`)")
        appendLine()
        appendLine(protocol.documentation)

        appendLine("---")
        appendLine()
        appendLine("## Packet Types")

        val types = packets.flatMap { it.fields }.map { it.type }.distinct().sortedBy { it }
        for ((index, type) in types.withIndex()) {
            val typeDef = protocol.types.firstOrNull { it.name == type }
            appendLine("### ${index + 1}. Type `${type}`")
            appendLine(typeDef?.documentation ?: "No documentation available.")
            appendLine()
        }

        appendLine()
        appendLine("## Packets")

        for ((index, packet) in packets.withIndex()) {
            appendLine()
            appendLine("### ${index + 1}. `${packet.name}` [${packet.direction.friendlyName}]")
            appendLine(packet.documentation)
            appendLine()
            appendLine("| Name | Type | Description |")
            appendLine("| ---- | ---- | ----------- |")
            for (field in packet.fields) {
                appendLine("| `${field.name}` | `${field.type}` | ${field.documentation.trim()} |")
            }

        }

    }

}