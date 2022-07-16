package io.github.orioncraftmc.tmicn.definitions.workspace

import org.gradle.api.Project
import java.nio.file.Path

data class TmicnDefinitionsWorkspace(
    val project: Project,
) {
    private val definitionSpecs = mutableListOf<ProtocolDefinitionSpec>()
    internal val protocolDefinitions = mutableListOf<ProtocolDefinition>()

    fun addProtocol(name: String, protocolRootDir: Path) {
        definitionSpecs.add(ProtocolDefinitionSpec(project, name, protocolRootDir))
        project.logger.quiet(":tmicn - Registered TMICN protocol \"$name\"")
    }

    fun loadProtocols() {
        definitionSpecs.forEach {
            protocolDefinitions.add(it.load())
        }
    }

}
