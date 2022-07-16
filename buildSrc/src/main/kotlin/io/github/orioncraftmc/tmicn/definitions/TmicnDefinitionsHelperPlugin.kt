package io.github.orioncraftmc.tmicn.definitions

import io.github.orioncraftmc.tmicn.definitions.extensions.ProtocolDefinitionsExtension
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceSetupHelper
import io.github.orioncraftmc.tmicn.definitions.tasks.impl.GenerateMarkdownDocumentation
import org.gradle.api.Plugin
import org.gradle.api.Project

class TmicnDefinitionsHelperPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("tmicn", ProtocolDefinitionsExtension::class.java)

        target.afterEvaluate {
            val workspace = WorkspaceSetupHelper.setupWorkspace(it, extension)
            extension.workspace = workspace
            target.tasks.register("generateMarkdownDocumentation", GenerateMarkdownDocumentation::class.java)
        }
    }
}