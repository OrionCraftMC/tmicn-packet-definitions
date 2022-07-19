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
        TmicnPacketMultiplexingModel(definitionModel, packet.multiplexing)
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

    val asType: TypeSpec by lazy {
        TypeSpec.classBuilder(packetName).also { if (packet.fields.isNotEmpty()) it.addModifiers(KModifier.DATA) }
            .addSuperinterface(KotlinConstants.tmicnPacketInterface).primaryConstructor(asCtor)
            .addProperties(fields.map { it.asProperty }).addKdoc(packet.documentation)
            .addType(asPacketIoModel.asType)
            .build()
    }



    val asIoRead: CodeBlock by lazy {
        CodeBlock.of("%T.read(dis)", this.asClassName)
    }
}
