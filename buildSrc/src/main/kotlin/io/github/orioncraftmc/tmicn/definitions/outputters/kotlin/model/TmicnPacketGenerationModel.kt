package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField
import java.io.DataInputStream


data class TmicnPacketIoGenerationModel(
    val model: TmicnPacketGenerationModel
) {
    val inputStreamParameter by lazy {
        ParameterSpec.builder("dis", DataInputStream::class).build()
    }

    val asReadFieldsCodeBlock by lazy {
        CodeBlock.builder()
            .add("return %T(", model.asClassName).add("\n")
            .indent()
            .also {
                for (field in model.fields) {
                    it.add(field.asFieldTypeIoRead)
                    it.addStatement(",")
                }
            }
            .unindent()
            .add(")")
            .build()
    }

    val asReadFunc by lazy {
        FunSpec.builder("read")
            .addModifiers(KModifier.OVERRIDE)
            .returns(model.asClassName)
            .addParameter(inputStreamParameter)
            .addCode(asReadFieldsCodeBlock)
            .build()
    }

    val asType: TypeSpec by lazy {
        TypeSpec.companionObjectBuilder()
            .addSuperinterface(KotlinConstants.tmicnPacketIoInterface.parameterizedBy(model.asClassName))
            .addFunction(asReadFunc)
            .build()
    }
}

data class TmicnPacketGenerationModel(
    val definition: ProtocolDefinition,
    val packet: TmicnPacket,
    val fields: List<TmicnPacketFieldGenerationModel> = packet.fields.map {
        TmicnPacketFieldGenerationModel(definition, packet, it)
    },
    val packageName: String = KotlinConstants.generatedPacketPackage(definition, packet),
    val packetName: String = "${KotlinConstants.cleanupPacketName(packet.name)}Packet",
) {
    val asPacketIoModel: TmicnPacketIoGenerationModel by lazy {
        TmicnPacketIoGenerationModel(this)
    }

    val asClassName by lazy {
        ClassName(packageName, packetName)
    }

    val asCtor: FunSpec by lazy {
        FunSpec.constructorBuilder()
            .addParameters(fields.map { it.asParameter })
            .build()
    }

    val asType: TypeSpec by lazy {
        TypeSpec.classBuilder(packetName).also { if (packet.fields.isNotEmpty()) it.addModifiers(KModifier.DATA) }
            .addSuperinterface(KotlinConstants.tmicnPacketInterface).primaryConstructor(asCtor)
            .addProperties(fields.map { it.asProperty }).addKdoc(packet.documentation)
            .addType(asPacketIoModel.asType)
            .build()
    }
}

data class TmicnPacketFieldGenerationModel(
    val definition: ProtocolDefinition, val packet: TmicnPacket, val field: TmicnPacketField, val fieldName: String = KotlinConstants.cleanupPacketFieldName(
        field.name
    )
) {
    val fieldType: TypeName by lazy {
        definition.protocol.types.firstOrNull { it.name == this.field.type }?.let { KotlinConstants.parseJavaName(it.javaName) }
            ?: ClassName.bestGuess(this.field.type)
    }

    val fieldTypeIo: TypeName by lazy {
        definition.protocol.types.firstOrNull { it.name == this.field.type }?.ioClass?.let { ClassName.bestGuess(it) }
            ?: NOTHING
    }

    val asFieldTypeIoRead: CodeBlock by lazy {
        if (fieldTypeIo == NOTHING) {
            CodeBlock.of("TODO()")
        } else {
            CodeBlock.of("%T.read(dis)", fieldTypeIo)
        }
    }

    val asParameter: ParameterSpec by lazy {
        ParameterSpec.builder(fieldName, fieldType).addKdoc(field.documentation).build()
    }

    val asProperty: PropertySpec by lazy {
        PropertySpec.builder(fieldName, fieldType).addKdoc(field.documentation).initializer(fieldName).mutable(true)
            .build()
    }
}