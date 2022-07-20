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

    val asSecondaryCtor: FunSpec? by lazy {
        if (!multiplexingModel.isMultiplexingPackets) return@lazy null
        val parameterSpecs =
            fields.filterNot { it.isMultiplexingIdField }
                .map { it.asParameter }

        FunSpec.constructorBuilder()
            .addParameters(parameterSpecs)
            .callThisConstructor(fields.map { if (it.isMultiplexingIdField) CodeBlock.of("%L", multiplexingModel.multiplexingData?.startId ?: 0) else CodeBlock.of("%N", it.asParameter) })
            .build()
    }

    val asPacketMultiplexedByAnnotation : AnnotationSpec? by lazy {
        val packetThatDefinesMultiplexing = this.definitionModel.packets.firstOrNull { it.multiplexingModel.multiplexedPackets.containsValue(this) }
            ?: return@lazy null

        AnnotationSpec.builder(KotlinConstants.tmicnPacketMultiplexedByMetadata)
            .addMember("%T::class", packetThatDefinesMultiplexing.asClassName)
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
            .addSuperinterface(KotlinConstants.tmicnPacketInterface)
            .primaryConstructor(asCtor)
            .also { asSecondaryCtor?.let { ctor -> it.addFunction(ctor) } }
            .addProperties(fields.map { it.asProperty }).addKdoc(packet.documentation)
            .addType(asPacketIoModel.asType)
            .addAnnotation(asPacketDirectionAnnotation)
            .also { asPacketMultiplexedByAnnotation?.let { anno -> it.addAnnotation(anno) } }
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
