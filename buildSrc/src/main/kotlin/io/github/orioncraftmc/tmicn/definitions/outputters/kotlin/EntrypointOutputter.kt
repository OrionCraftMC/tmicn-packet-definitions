package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition


fun entrypoint(definition: ProtocolDefinition): FileSpec {
    val (name, protocol, _) = definition
    val packageName = KotlinConstants.generatedProtocolPackage(name)
    val entrypointName = "${KotlinConstants.cleanupProtocolName(protocol.name)}Protocol"

    return FileSpec.builder(packageName, entrypointName)
        .addType(
            TypeSpec.objectBuilder(entrypointName)
                .addKdoc(protocol.documentation)
                .build()
        ).build()

}