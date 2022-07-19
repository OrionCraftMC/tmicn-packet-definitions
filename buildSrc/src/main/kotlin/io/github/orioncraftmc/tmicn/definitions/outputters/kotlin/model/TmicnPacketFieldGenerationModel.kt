package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField

data class TmicnPacketFieldGenerationModel(
    val definition: ProtocolDefinition,
    val packet: TmicnPacket,
    val field: TmicnPacketField,
    val fieldName: String = WorkspaceConstants.KotlinConstants.cleanupPacketFieldName(
        field.name
    )
) {
    val fieldType: TypeName by lazy {
        definition.protocol.types.firstOrNull { it.name == this.field.type }?.javaName?.let {
            WorkspaceConstants.KotlinConstants.parseJavaName(
                it
            )
        } ?: NOTHING
    }

    val fieldTypeIo: TypeName by lazy {
        definition.protocol.types.firstOrNull { it.name == this.field.type }?.ioClass?.let { ClassName.bestGuess(it) }
            ?: NOTHING
    }

    val asParameter: ParameterSpec by lazy {
        ParameterSpec.builder(fieldName, fieldType).addKdoc(field.documentation).build()
    }

    val asProperty: PropertySpec by lazy {
        PropertySpec.builder(fieldName, fieldType).addKdoc(field.documentation).initializer(fieldName).mutable(true)
            .build()
    }
}