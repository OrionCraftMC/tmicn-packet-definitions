package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.KotlinConstants
import io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model.TmicnPacketGenerationModel
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import io.github.orioncraftmc.tmicn.definitions.workspace.definitions.packets.TmicnPacket
import java.io.DataOutputStream

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

    val model = TmicnPacketGenerationModel(definition, packet)

    return FileSpec.get(
        model.packageName, model.asType
    )

}
