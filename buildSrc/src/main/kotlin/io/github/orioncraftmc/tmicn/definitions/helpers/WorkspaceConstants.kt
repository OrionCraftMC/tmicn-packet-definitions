package io.github.orioncraftmc.tmicn.definitions.helpers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
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

        val tmicnPacketInterface = ClassName.bestGuess("io.github.orioncraftmc.tellmeicheatnow.core.packets.TmicnPacket")
        val tmicnPacketIoInterface = ClassName.bestGuess("io.github.orioncraftmc.tellmeicheatnow.core.packets.TmicnPacketIo")

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

        fun parseJavaName(javaName: String): TypeName {
            val genericsStart = javaName.indexOf('<')
            val genericsEnd = javaName.indexOf('>')
            if (genericsStart != -1 && genericsEnd != -1) {
                val generics = javaName.substring(genericsStart + 1, genericsEnd)
                val className = javaName.substring(0, genericsStart)
                return (parseJavaName(className) as ClassName).parameterizedBy(generics.split(',').map { parseJavaName(it.trim()) })
            }
            return ClassName.bestGuess(javaName)
        }

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