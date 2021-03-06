package io.github.orioncraftmc.tmicn.definitions.tasks.impl

import io.github.orioncraftmc.tmicn.definitions.extensions.tmicn
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.outputters.MarkdownOutputter
import io.github.orioncraftmc.tmicn.definitions.tasks.TmicnTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

open class GenerateMarkdownDocumentation : TmicnTask() {
    init {
        description = "Generates the markdown documentation for the protocol definitions"
    }

    @TaskAction
    fun generate() {
        val docsOutDir = project.buildDir.toPath().resolve(WorkspaceConstants.MarkdownConstants.DOCS_OUTPUT_DIR)
        val tmicn = project.tmicn

        Files.createDirectories(docsOutDir)

        val workspace = tmicn.workspace ?: throw IllegalStateException("Workspace is not ready yet")
        for (protocolDefinition in workspace.protocolDefinitions) {
            MarkdownOutputter.output(protocolDefinition, docsOutDir)
        }
    }

}