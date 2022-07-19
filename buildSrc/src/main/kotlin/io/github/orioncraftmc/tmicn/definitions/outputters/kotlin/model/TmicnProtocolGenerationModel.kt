package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField


data class TmicnProtocolGenerationModel(
    val definition: ProtocolDefinition,
    val packet: TmicnPacket,
    val fields: List<TmicnPacketFieldGenerationModel> = packet.fields.map {
        TmicnPacketFieldGenerationModel(definition, packet, it)
    },
    val packageName: String = KotlinConstants.generatedPacketPackage(definition, packet),
    val packetName: String = "${KotlinConstants.cleanupPacketName(packet.name)}Packet",
) {
    val asCtor: FunSpec by lazy {
        FunSpec.constructorBuilder()
            .addParameters(fields.map { it.asParameter })
            .build()
    }

    val asType: TypeSpec by lazy {
        TypeSpec.classBuilder(packetName).also { if (packet.fields.isNotEmpty()) it.addModifiers(KModifier.DATA) }
            .addSuperinterface(KotlinConstants.tmicnPacketInterface).primaryConstructor(asCtor)
            .addProperties(fields.map { it.asProperty }).addKdoc(packet.documentation)
            //TODO: .addType(packetIoObject(packageName, name))
            .build()
    }
}

data class TmicnPacketFieldGenerationModel(
    val definition: ProtocolDefinition, val packet: TmicnPacket, val field: TmicnPacketField, val fieldName: String = KotlinConstants.cleanupPacketFieldName(
        field.name
    )
) {
    val fieldType: TypeName by lazy {
        definition.protocol.types.firstOrNull { it.name == this.field.type }?.let { ClassName.bestGuess(it.javaName) }
            ?: NOTHING
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