package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket

data class TmicnPacketGenerationModel(
    val definitionModel: TmicnProtocolDefinitionGenerationModel,
    val packet: TmicnPacket,
    val fields: List<TmicnPacketFieldGenerationModel> = packet.fields.map {
        TmicnPacketFieldGenerationModel(definitionModel.definition, packet, it)
    },
    val packageName: String = KotlinConstants.generatedPacketPackage(definitionModel.definition, packet),
    val packetName: String = "${KotlinConstants.cleanupPacketName(packet.name)}Packet",
) {

    val multiplexingModel by lazy {
        TmicnPacketMultiplexingModel(definitionModel, this)
    }

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

    val asPacketDirectionAnnotation: AnnotationSpec by lazy {
        AnnotationSpec.builder(KotlinConstants.tmicnPacketDirectionMetadata)
            .also {
                val dirs = packet.directions.map { CodeBlock.of("%T.%L", KotlinConstants.tmicnPacketDirectionEnum, it.name) }
                it.addMember("[${"%L, ".repeat(dirs.size)}]", *dirs.toTypedArray())
            }
            .build()
    }

    val asType: TypeSpec by lazy {
        TypeSpec.classBuilder(packetName).also { if (packet.fields.isNotEmpty()) it.addModifiers(KModifier.DATA) }
            .addSuperinterface(KotlinConstants.tmicnPacketInterface).primaryConstructor(asCtor)
            .addProperties(fields.map { it.asProperty }).addKdoc(packet.documentation)
            .addType(asPacketIoModel.asType)
            .addAnnotation(asPacketDirectionAnnotation)
            .build()
    }


    fun getAsIoRead(name: ParameterSpec): CodeBlock = CodeBlock.of("%T.read(%N)", this.asClassName, name)
    fun getAsIoWrite(outStream: ParameterSpec, packetVariable: CodeBlock, expectedPacketType: TypeName): CodeBlock {
        val packetTypeToWrite = this.asClassName
        return CodeBlock.of(
            "%T.write(%N, %L as %T)",
            packetTypeToWrite,
            outStream,
            packetVariable,
            expectedPacketType
        )
    }
}
