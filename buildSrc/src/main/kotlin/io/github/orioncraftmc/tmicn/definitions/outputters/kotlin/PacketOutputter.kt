package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin

import com.squareup.kotlinpoet.*
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.field.TmicnPacketField
import kotlin.reflect.KClass

private fun resolveFieldType(definition: ProtocolDefinition, packet: TmicnPacket, field: TmicnPacketField): KClass<Any> {
    return Any::class
}

private fun resolveFieldName(field: TmicnPacketField): String {
    return KotlinConstants.cleanupPacketFieldName(field.name)
}

fun packet(definition: ProtocolDefinition, packet: TmicnPacket): FileSpec {
    val packageName = KotlinConstants.generatedPacketPackage(definition, packet)
    val name = "${KotlinConstants.cleanupPacketName(packet.name)}Packet"

    return FileSpec.get(
        packageName, TypeSpec.classBuilder(name)
            .addModifiers(KModifier.DATA)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(packet.fields.map {
                        ParameterSpec.builder(resolveFieldName(it), resolveFieldType(definition, packet, it))
                            .addKdoc(it.documentation)
                            .build()
                    })
                    .build()
            )
            .addProperties(packet.fields.map {
                PropertySpec.builder(resolveFieldName(it), resolveFieldType(definition, packet, it))
                    .addKdoc(it.documentation)
                    .initializer(resolveFieldName(it))
                    .mutable(true)
                    .build()
            })
            .build()
    )

}