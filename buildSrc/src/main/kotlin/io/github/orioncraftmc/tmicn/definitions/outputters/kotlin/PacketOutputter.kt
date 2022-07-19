package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField
import java.io.DataOutputStream

private fun resolveFieldType(definition: ProtocolDefinition, packet: TmicnPacket, field: TmicnPacketField): TypeName {
    return definition.protocol.types
        .firstOrNull { it.name == field.type }?.let { ClassName.bestGuess(it.javaName) }
        ?: NOTHING
}

private fun resolveFieldName(field: TmicnPacketField): String {
    return KotlinConstants.cleanupPacketFieldName(field.name)
}

private fun packetCtor(packet: TmicnPacket, definition: ProtocolDefinition) =
    FunSpec.constructorBuilder()
        .addParameters(packet.fields.map {
            ParameterSpec.builder(resolveFieldName(it), resolveFieldType(definition, packet, it))
                .addKdoc(it.documentation)
                .build()
        })
        .build()

private fun packetField(definition: ProtocolDefinition, packet: TmicnPacket, field: TmicnPacketField) =
    PropertySpec.builder(resolveFieldName(field), resolveFieldType(definition, packet, field))
        .addKdoc(field.documentation)
        .initializer(resolveFieldName(field))
        .mutable(true)
        .build()

private fun packetIoObject(packageName: String, name: String): TypeSpec {
    val packetIoType =
        KotlinConstants.tmicnPacketIoInterface.parameterizedBy(ClassName.bestGuess("$packageName.$name"))

    val dosParam = ParameterSpec.builder("dos", DataOutputStream::class.asTypeName()).build()

    val writeFunc = FunSpec.builder("write")
        .addParameter(dosParam)


    return TypeSpec
        .companionObjectBuilder()
        .addSuperinterface(packetIoType)
        .addFunction(writeFunc.build())
        .build()
}

fun packet(definition: ProtocolDefinition, packet: TmicnPacket): FileSpec {
    val packageName = KotlinConstants.generatedPacketPackage(definition, packet)
    val name = "${KotlinConstants.cleanupPacketName(packet.name)}Packet"

    val packetType = TypeSpec.classBuilder(name)
        .also { if (packet.fields.isNotEmpty()) it.addModifiers(KModifier.DATA) }
        .addSuperinterface(KotlinConstants.tmicnPacketInterface)
        .primaryConstructor(packetCtor(packet, definition))
        .addProperties(packet.fields.map { packetField(definition, packet, it) })
        .addKdoc(packet.documentation)
        .addType(packetIoObject(packageName, name))
        .build()
    return FileSpec.get(
        packageName, packetType
    )

}
