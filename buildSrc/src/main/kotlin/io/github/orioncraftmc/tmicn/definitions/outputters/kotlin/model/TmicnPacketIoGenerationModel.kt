package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants.MultiplexConstants
import java.io.DataInputStream

data class TmicnPacketIoGenerationModel(
    val model: TmicnPacketGenerationModel
) {
    val inputStreamParameter by lazy {
        ParameterSpec.builder("dis", DataInputStream::class).build()
    }

    val asReadFieldsCodeBlock by lazy {
        val builder = CodeBlock.builder()

        if (model.multiplexingModel.isMultiplexingPackets) {
            val multiplexIdField =
                model.fields.firstOrNull { it.field.name == MultiplexConstants.MULTIPLEX_ID_FIELD_NAME }
            val multiplexContentField =
                model.fields.firstOrNull { it.field.type == MultiplexConstants.MULTIPLEX_CONTENT_TYPE }

            require(multiplexIdField != null) { "Multiplexing packets must have a multiplex id field named ${MultiplexConstants.MULTIPLEX_ID_FIELD_NAME}" }
            require(multiplexContentField != null) { "Multiplexing packets must have a multiplex content field with type ${MultiplexConstants.MULTIPLEX_CONTENT_TYPE}" }

            for (field in model.fields.filter { it.field.type != MultiplexConstants.MULTIPLEX_CONTENT_TYPE }) {
                builder.add("val %N = ", field.fieldName)
                builder.add(field.asFieldTypeIoRead)
                builder.add("\n")
            }

            builder.beginControlFlow(
                "val %N = when (%N)",
                multiplexContentField.asParameter,
                multiplexIdField.fieldName
            )

            for ((id, multiplexedPacket) in model.multiplexingModel.multiplexedPackets) {
                builder.addStatement("%L -> %L", id, multiplexedPacket.asIoRead)
            }
            builder.addStatement(
                "else -> throw %T(%P)",
                IllegalStateException::class,
                "Unknown multiplex id \$multiplexId"
            )
            builder.endControlFlow()
        }

        builder.add("return %T(", model.asClassName).add("\n")
        builder.indent()
        for (field in model.fields) {
            if (model.multiplexingModel.isMultiplexingPackets) {
                builder.addStatement("%N,", field.asParameter)
            } else {
                builder.addStatement("%L,", field.asFieldTypeIoRead)
            }
        }
        builder.unindent()
        builder.add(")")
        builder.build()
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
            .addSuperinterface(WorkspaceConstants.KotlinConstants.tmicnPacketIoInterface.parameterizedBy(model.asClassName))
            .addFunction(asReadFunc)
            .build()
    }
}