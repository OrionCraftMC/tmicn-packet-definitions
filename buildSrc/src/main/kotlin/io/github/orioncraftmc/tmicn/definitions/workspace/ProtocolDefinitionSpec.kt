package io.github.orioncraftmc.tmicn.definitions.workspace

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.TmicnProtocol
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import org.gradle.api.Project
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.readText

data class ProtocolDefinitionSpec(
    val project: Project,
    val name: String,
    val rootDir: Path
) {
    private val mapper = TomlMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerModule(kotlinModule())
    }

    private val definitionPath: Path = rootDir.resolve(WorkspaceConstants.PROTOCOL_DEFINITION_FILE)
    private val dataDir: Path = rootDir.resolve(WorkspaceConstants.PROTOCOL_DATA_DIR)

    fun load(): ProtocolDefinition {
        if (Files.notExists(definitionPath)) {
            throw IllegalStateException("Protocol definition file does not exist: $definitionPath")
        }

        val protocol = mapper.readValue(definitionPath.readText(), TmicnProtocol::class.java)

        val packets = mutableListOf<TmicnPacket>()
        Files.walk(dataDir).forEach {
            if (it.extension == "toml") {
                val packet = mapper.readValue(it.readText(), TmicnPacket::class.java)
                packets.add(packet)
                project.logger.quiet(":tmicn - Loaded packet: ${packet.name}")
            }
        }

        return ProtocolDefinition(name, protocol, packets)
    }
}