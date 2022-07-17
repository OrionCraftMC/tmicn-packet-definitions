package io.github.orioncraftmc.tmicn.definitions.outputters

import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocol
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocolType
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.writeText

object MarkdownOutputter : Outputter {

    private fun StringBuilder.splitter() = apply {
        appendLine()
        appendLine("---")
        appendLine()
    }

    private fun StringBuilder.protocolHeader(definition: ProtocolDefinition, protocol: TmicnProtocol) = apply {
        appendLine("# TMICN Protocol Definition - ${protocol.name} (`${definition.name}`)")
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

    private fun StringBuilder.protocolPacket(packet: TmicnPacket) {
        fun StringBuilder.protocolPacketHeader(packet: TmicnPacket) {
            appendLine("# `${packet.name}` [${packet.directions.joinToString { it.friendlyName }}]")
            appendLine("Plugin message channel: ${packet.pluginMessageChannel?.let { "`$it`" } ?: "No stable plugin message channel"}")
            appendLine()
            appendLine("## Documentation")
            appendLine(packet.documentation)
        }

        fun StringBuilder.protocolPacketFields(packet: TmicnPacket) {
            appendLine("## Fields")
            if (packet.fields.isEmpty()) {
                appendLine("This packet has no fields.")
            } else {
                appendLine("| Name | Type | Documentation |")
                appendLine("| ---- | ---- | ------------- |")
                for (field in packet.fields) {
                    appendLine("| `${field.name}` | `${field.type}` | ${field.documentation.trim()} |")
                }
            }
        }

        fun StringBuilder.protocolPacketMultiplexing(packet: TmicnPacket) {
            if (packet.multiplexing != null) {
                appendLine()
                appendLine("## Multiplexing specification")
                appendLine()
                appendLine("This packet performs multiplexing. Meaning it is used to wrap multiple different packets under the same one.")
                appendLine()
                appendLine("|  Id  | Name |")
                appendLine("| ---- | ---- |")
                val startId = packet.multiplexing.startId
                for ((index, id) in (startId until startId + packet.multiplexing.packets.size).withIndex()) {
                    appendLine("| `$id` | `${packet.multiplexing.packets[index]}` |")
                }
            }
        }

        protocolPacketHeader(packet)
        appendLine()
        protocolPacketFields(packet)
        protocolPacketMultiplexing(packet)
    }


    private fun StringBuilder.packetEntrypointLink(packet: TmicnPacket, fullPacketDocDir: Path) {
        with(this) {
            appendLine("### Packet `${packet.name}`")
            appendLine(
                "The documentation for this packet can be found [↪️ here](${
                    fullPacketDocDir.toString().replace('\\', '/').replace(" ", "%20")
                })."
            )
        }
    }

    override fun output(definition: ProtocolDefinition, output: Path) {
        val protocol = definition.protocol

        val protocolDir = output.resolve(definition.name)
        Files.createDirectories(protocolDir)

        val entrypointFile = protocolDir.resolve(WorkspaceConstants.PROTOCOL_MARKDOWN_FILE)
        val typesFile = protocolDir.resolve(WorkspaceConstants.TYPES_MARKDOWN_FILE)

        entrypointFile.writeText(buildString {
            protocolHeader(definition, protocol)
            splitter()
            appendLine("Looking for types? Check them out [↪️ here](${WorkspaceConstants.TYPES_MARKDOWN_FILE}).")
            appendLine()
            appendLine("Looking for the packets? Check them out [↪️ here](${WorkspaceConstants.PROTOCOL_PACKETS_FILE}).")
        })

        typesFile.writeText(buildString {
            protocolPacketTypes(definition, protocol)
        })

        val packetsEntrypointFile = protocolDir.resolve(WorkspaceConstants.PROTOCOL_PACKETS_FILE)
        val packetsEntrypointBuilder = StringBuilder().apply {
            appendLine("# TMICN Protocol Packets - ${protocol.name} (`${definition.name}`)")
            appendLine("## Packets")
            appendLine()
            appendLine("Here you can find all the packets that are part of the protocol, summarized by their name.")
            appendLine()
        }

        val packetsDir = protocolDir.resolve(WorkspaceConstants.PROTOCOL_PACKETS_DIR)
        Files.createDirectories(packetsDir)
        for (packet in definition.packets.sortedBy { it.name }) {
            val packetFile = (packet.path.parent?.let { packetsDir.resolve(it) } ?: packetsDir).resolve("${packet.path.nameWithoutExtension}.md")
            val fullPacketDocDir = protocolDir.relativize(packetFile)
            Files.createDirectories(packetFile.parent)

            packetsEntrypointBuilder.packetEntrypointLink(packet, fullPacketDocDir)

            packetFile.writeText(buildString {
                protocolPacket(packet)
            })
        }

        packetsEntrypointFile.writeText(packetsEntrypointBuilder.toString())

    }
}