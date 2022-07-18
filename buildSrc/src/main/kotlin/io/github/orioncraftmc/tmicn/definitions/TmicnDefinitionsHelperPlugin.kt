package io.github.orioncraftmc.tmicn.definitions

import io.github.orioncraftmc.tmicn.definitions.extensions.ProtocolDefinitionsExtension
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceSetupHelper
import io.github.orioncraftmc.tmicn.definitions.tasks.impl.GenerateKotlinCode
import io.github.orioncraftmc.tmicn.definitions.tasks.impl.GenerateMarkdownDocumentation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class TmicnDefinitionsHelperPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("tmicn", ProtocolDefinitionsExtension::class.java)

        target.extensions.getByType(JavaPluginExtension::class.java).sourceSets.all {
            it.java.srcDir(target.buildDir.resolve(WorkspaceConstants.KotlinConstants.GENERATED_DIR))
        }

        target.afterEvaluate {
            val workspace = WorkspaceSetupHelper.setupWorkspace(it, extension)
            extension.workspace = workspace
            target.tasks.register("generateMarkdownDocumentation", GenerateMarkdownDocumentation::class.java)
            target.tasks.register("generateKotlinCode", GenerateKotlinCode::class.java)
        }
    }
}