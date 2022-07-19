package io.github.orioncraftmc.tmicn.definitions

import io.github.orioncraftmc.tmicn.definitions.extensions.ProtocolDefinitionsExtension
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants.GENERATED_DIR
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceSetupHelper
import io.github.orioncraftmc.tmicn.definitions.tasks.impl.GenerateKotlinCode
import io.github.orioncraftmc.tmicn.definitions.tasks.impl.GenerateMarkdownDocumentation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class TmicnDefinitionsHelperPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("tmicn", ProtocolDefinitionsExtension::class.java)

        target.afterEvaluate { project ->

            val workspace = WorkspaceSetupHelper.setupWorkspace(project, extension)

            workspace.protocolDefinitions.forEach { def ->
                target.extensions.getByType(JavaPluginExtension::class.java).sourceSets.create(def.name) { set ->
                    set.java.srcDir(target.buildDir.toPath().resolve(GENERATED_DIR).resolve(def.name))
                }
            }
            extension.workspace = workspace
            target.tasks.register("generateMarkdownDocumentation", GenerateMarkdownDocumentation::class.java)
            target.tasks.register("generateKotlinCode", GenerateKotlinCode::class.java)
        }
    }
}