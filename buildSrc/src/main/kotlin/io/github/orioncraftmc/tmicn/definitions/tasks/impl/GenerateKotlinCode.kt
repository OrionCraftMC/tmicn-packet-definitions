package io.github.orioncraftmc.tmicn.definitions.tasks.impl

import io.github.orioncraftmc.tmicn.definitions.extensions.tmicn
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.KotlinOutputter
import io.github.orioncraftmc.tmicn.definitions.tasks.TmicnTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

open class GenerateKotlinCode : TmicnTask() {

    @TaskAction
    fun generate() {
        val outDir = project.buildDir.toPath().resolve(WorkspaceConstants.KotlinConstants.GENERATED_DIR)
        outDir.toFile().deleteRecursively()

        val tmicn = project.tmicn

        Files.createDirectories(outDir)

        val workspace = tmicn.workspace ?: throw IllegalStateException("Workspace is not ready yet")
        for (protocolDefinition in workspace.protocolDefinitions) {
            KotlinOutputter.output(protocolDefinition, outDir)
        }


    }

}