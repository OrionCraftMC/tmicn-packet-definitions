package io.github.orioncraftmc.tmicn.definitions.helpers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

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


}