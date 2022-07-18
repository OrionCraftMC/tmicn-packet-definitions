package io.github.orioncraftmc.tmicn.definitions.helpers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocolType

object WorkspaceConstants {

    val mapper = TomlMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerModule(kotlinModule())
    }

    const val PROTOCOL_DEFINITION_FILE = "protocol.toml"

    const val TMICN_DEFINITION_FILE = "src/tmicn.toml"

    const val PROTOCOL_DATA_DIR = "data"

    const val DOCS_OUTPUT_DIR = "markdown"

    const val PROTOCOL_PACKETS_DIR = "packets"

    const val PROTOCOL_MARKDOWN_FILE = "protocol.md"
    const val TYPES_MARKDOWN_FILE = "types.md"
    const val PROTOCOL_PACKETS_FILE = "packets.md"

    private const val MULTIPLEX_CONTENT_TYPE = "multiplex_content"
    val MULTIPLEX_TYPE = TmicnProtocolType(MULTIPLEX_CONTENT_TYPE, """
        |This is a multiplex content type.
        |
        |The field marked with this type is replaced with the actual byte content from the multiplexed packet""".trimMargin().trim())


}