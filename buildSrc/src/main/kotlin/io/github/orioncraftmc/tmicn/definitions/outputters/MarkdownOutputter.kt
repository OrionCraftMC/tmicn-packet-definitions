package io.github.orioncraftmc.tmicn.definitions.outputters

import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocol
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocolType
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import java.nio.file.Path
import kotlin.io.path.writeText

object MarkdownOutputter : Outputter {

    private fun StringBuilder.splitter() = apply {
        appendLine()
        appendLine("---")
        appendLine()
    }

    private fun StringBuilder.protocolHeader(definition: ProtocolDefinition, protocol: TmicnProtocol) = apply {
        appendLine("# ${protocol.name} (`${definition.name}`)")
        appendLine()
        appendLine(protocol.documentation.trimEnd())
    }

    private fun StringBuilder.protocolPacketTypes(definition: ProtocolDefinition, protocol: TmicnProtocol) = apply {
        fun StringBuilder.protocolType(index: Int, type: String, typeDef: TmicnProtocolType?) {
            appendLine("### ${index + 1}. Type `${type}`")
            appendLine(typeDef?.documentation?.trimEnd() ?: "No documentation available.")
        }

        appendLine("## Packet Types")

        // Get all types used, and sort them by name (for consistency)
        val types =
            definition.packets.flatMap { it.fields }.map { it.type }.distinct().sortedBy { it }

        for ((index, type) in types.withIndex()) {
            appendLine()
            val typeDef = protocol.types.firstOrNull { it.name == type }
            protocolType(index, type, typeDef)
        }
    }

    private fun StringBuilder.protocolPackets(definition: ProtocolDefinition) = apply {

        fun StringBuilder.protocolPacketHeader(index: Int, packet: TmicnPacket) {
            appendLine("### ${index + 1}. `${packet.name}` [${packet.directions.joinToString { it.friendlyName }}]")
            appendLine("Plugin message channel: ${packet.pluginMessageChannel?.let { "`$it`" } ?: "No stable plugin message channel"}")
            appendLine()
            appendLine("#### Documentation")
            appendLine(packet.documentation)
        }

        fun StringBuilder.protocolPacketFields(packet: TmicnPacket) {
            appendLine("#### Fields")
            appendLine("| Name | Type | Documentation |")
            appendLine("| ---- | ---- | ------------- |")
            for (field in packet.fields) {
                appendLine("| `${field.name}` | `${field.type}` | ${field.documentation.trim()} |")
            }
        }

        fun StringBuilder.protocolPacketMultiplexing(packet: TmicnPacket) {
            if (packet.multiplexing != null) {
                appendLine()
                appendLine("This packet performs multiplexing. Meaning it is used to wrap multiple different packets under the same one.")
                appendLine()
                appendLine("#### Multiplexing specification")
                appendLine("|  Id  | Name |")
                appendLine("| ---- | ---- |")
                val startId = packet.multiplexing.startId
                for ((index, id) in (startId until startId + packet.multiplexing.packets.size).withIndex()) {
                    appendLine("| `$id` | `${packet.multiplexing.packets[index]}` |")
                }
            }
        }

        fun StringBuilder.protocolPacket(index: Int, packet: TmicnPacket) {
            protocolPacketHeader(index, packet)
            appendLine()
            protocolPacketFields(packet)
            protocolPacketMultiplexing(packet)
        }


        val packets = definition.packets.sortedBy { it.name }

        appendLine("## Packets")

        for ((index, packet) in packets.withIndex()) {
            appendLine()
            protocolPacket(index, packet)
        }
    }

    override fun output(definition: ProtocolDefinition, output: Path) {
        val outputFile = output.resolve(definition.name + ".md")
        val protocol = definition.protocol

        val result = buildString {

            protocolHeader(definition, protocol)
            splitter()
            protocolPacketTypes(definition, protocol)
            splitter()
            protocolPackets(definition)

        }
        outputFile.writeText(result)
    }


}