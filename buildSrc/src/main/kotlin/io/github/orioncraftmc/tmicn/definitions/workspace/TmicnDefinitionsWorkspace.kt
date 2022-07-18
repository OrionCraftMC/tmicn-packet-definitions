package io.github.orioncraftmc.tmicn.definitions.workspace

import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import org.gradle.api.Project
import java.nio.file.Path
import kotlin.io.path.readText

data class TmicnDefinitionsWorkspace(
    val project: Project,
) {
    private val tmicnSpecPath = project.projectDir.toPath().resolve(WorkspaceConstants.IoConstants.TMICN_DEFINITION_FILE)

    private val tmicnSpec = WorkspaceConstants.mapper.readValue(tmicnSpecPath.readText(), TmicnSpec::class.java)

    init {
        tmicnSpec.types.add(WorkspaceConstants.MultiplexConstants.MULTIPLEX_TYPE)
    }

    private val definitionSpecs = mutableListOf<ProtocolDefinitionSpec>()
    internal val protocolDefinitions = mutableListOf<ProtocolDefinition>()

    fun addProtocol(name: String, protocolRootDir: Path) {
        definitionSpecs.add(ProtocolDefinitionSpec(project, name, protocolRootDir))
        project.logger.quiet(":tmicn - Registered TMICN protocol \"$name\"")
    }

    fun loadProtocols() {
        definitionSpecs.forEach {
            protocolDefinitions.add(it.load(tmicnSpec))
        }
    }

}
