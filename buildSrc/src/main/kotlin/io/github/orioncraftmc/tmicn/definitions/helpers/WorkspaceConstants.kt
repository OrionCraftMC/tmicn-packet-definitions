package io.github.orioncraftmc.tmicn.definitions.helpers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocolType
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import java.io.File
import java.util.*

object WorkspaceConstants {

    val mapper = TomlMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerModule(kotlinModule())
    }

    object IoConstants {
        const val PROTOCOL_DEFINITION_FILE = "protocol.toml"

        const val TMICN_DEFINITION_FILE = "src/tmicn/tmicn.toml"

        const val PROTOCOL_DATA_DIR = "data"
    }

    object MarkdownConstants {
        const val DOCS_OUTPUT_DIR = "markdown"

        const val PROTOCOL_PACKETS_DIR = "packets"

        const val PROTOCOL_MARKDOWN_FILE = "protocol.md"
        const val TYPES_MARKDOWN_FILE = "types.md"
        const val PROTOCOL_PACKETS_FILE = "packets.md"
    }

    object KotlinConstants {

        const val GENERATED_DIR = "generated/kotlin"

        fun cleanupProtocolName(name: String) = name.replace(" ", "")

        fun generatedProtocolPackage(name: String) = "io.github.orioncraftmc.tellmeicheatnow.protocols.$name"

        fun generatedPacketPackage(definition: ProtocolDefinition, packet: TmicnPacket): String {
            var pathPackages: String? = null
            if (packet.path.parent != null) {
                val pathParent = packet.path.parent.toString()
                pathPackages = pathParent.replace(File.separatorChar, '.').split('.')
                    .joinToString(".") { it.split('_').joinToString("") }
            }
            return "${generatedProtocolPackage(definition.name)}.packets${pathPackages?.let { ".$it" } ?: ""}"
        }

        fun cleanupPacketName(name: String) = name.toCamelCase()

        fun cleanupPacketFieldName(name: String) = name.toLowerCamelCase()

        private fun String.toLowerCamelCase() = toCamelCase().replaceFirstChar { it.lowercase(Locale.getDefault()) }

        private fun String.toCamelCase() =
            split('_').joinToString("") { str -> str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }

    }

    object MultiplexConstants {
        private const val MULTIPLEX_CONTENT_TYPE = "multiplex_content"

        val MULTIPLEX_TYPE = TmicnProtocolType(
            MULTIPLEX_CONTENT_TYPE, """
        |This is a multiplex content type.
        |
        |The field marked with this type is replaced with the actual byte content from the multiplexed packet""".trimMargin()
                .trim(), "kotlin.ByteArray"
        )
    }

}