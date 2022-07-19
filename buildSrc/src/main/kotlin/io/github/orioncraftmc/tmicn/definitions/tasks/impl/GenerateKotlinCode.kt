package io.github.orioncraftmc.tmicn.definitions.tasks.impl

import io.github.orioncraftmc.tmicn.definitions.extensions.tmicn
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants.GENERATED_DIR
import io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.KotlinOutputter
import io.github.orioncraftmc.tmicn.definitions.tasks.TmicnTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

open class GenerateKotlinCode : TmicnTask() {

    @TaskAction
    fun generate() {
        val generationDir = project.buildDir.toPath().resolve(GENERATED_DIR)
        generationDir.toFile().deleteRecursively()

        val tmicn = project.tmicn

        val workspace = tmicn.workspace ?: throw IllegalStateException("Workspace is not ready yet")
        for (protocolDefinition in workspace.protocolDefinitions) {
            val outDir = generationDir.resolve(protocolDefinition.name)
            Files.createDirectories(outDir)

            KotlinOutputter.output(protocolDefinition, outDir)
        }


    }

}