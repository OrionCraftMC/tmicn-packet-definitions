package io.github.orioncraftmc.tmicn.definitions.helpers

import io.github.orioncraftmc.tmicn.definitions.extensions.ProtocolDefinitionsExtension
import io.github.orioncraftmc.tmicn.definitions.workspace.TmicnDefinitionsWorkspace
import org.gradle.api.Project

object WorkspaceSetupHelper {

    fun setupWorkspace(project: Project, extension: ProtocolDefinitionsExtension): TmicnDefinitionsWorkspace {
        val workspace = TmicnDefinitionsWorkspace(project)

        extension.protocols.forEach {
            val protocolRootDir = project.file("src/$it").toPath()
            workspace.addProtocol(it, protocolRootDir)
        }

        workspace.loadProtocols()

        return workspace
    }

}