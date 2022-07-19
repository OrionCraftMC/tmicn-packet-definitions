package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin.model

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.orioncraftmc.tmicn.definitions.helpers.WorkspaceConstants
import java.io.DataInputStream
import java.io.DataOutputStream

data class TmicnPacketIoGenerationModel(
    val model: TmicnPacketGenerationModel
) {

    val inputStreamParameter by lazy {
        ParameterSpec.builder("dis", DataInputStream::class).build()
    }

    val outputStreamParameter by lazy {
        ParameterSpec.builder("dos", DataOutputStream::class).build()
    }

    val outputValueParameter by lazy {
        ParameterSpec.builder("value", model.asClassName).build()
    }

    fun TmicnPacketFieldGenerationModel.getAsFieldTypeIoRead(): CodeBlock {
        return if (field.type == WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE) {
            model.multiplexingModel.asReadPacket
        } else if (fieldTypeIo == NOTHING) {
            CodeBlock.of("TODO(%S)", "Field type reader class (io_class) is unknown")
        } else {
            CodeBlock.of("%T.read(%N)", fieldTypeIo, inputStreamParameter)
        }
    }

    private fun TmicnPacketFieldGenerationModel.getAsFieldTypeIoWrite(name: Any): CodeBlock {
        return if (field.type == WorkspaceConstants.MultiplexConstants.MULTIPLEX_CONTENT_TYPE) {
            model.multiplexingModel.asWritePacketContent
        } else if (field.name == WorkspaceConstants.MultiplexConstants.MULTIPLEX_ID_FIELD_NAME) {
            CodeBlock.of("%T.write(%N, %L)", fieldTypeIo, outputStreamParameter, model.multiplexingModel.getAsWritePacketId())
        } else if (fieldTypeIo == NOTHING) {
            CodeBlock.of("TODO(%S)", "Field type is unknown for field $fieldName")
        } else {
            CodeBlock.of("%T.write(%N, %N.%N)", fieldTypeIo, outputStreamParameter, outputValueParameter, name)
        }
    }

    val asWriteFieldsCodeBlock by lazy {
        val builder = CodeBlock.builder()

        for (field in model.fields) {
            builder.addStatement("%L", field.getAsFieldTypeIoWrite(field.asParameter))
        }

        builder.build()
    }

    val asReadFieldsCodeBlock by lazy {
        val builder = CodeBlock.builder()

        for (field in model.fields) {
            builder.add("val %N = %L\n", field.fieldName, field.getAsFieldTypeIoRead())
        }

        builder.add("return %T(", model.asClassName).add("\n")
        builder.indent()
        for (field in model.fields) {
            builder.add("%N,\n", field.fieldName)
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
    val asWriteFunc by lazy {
        FunSpec.builder("write")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(outputStreamParameter)
            .addParameter(outputValueParameter)
            .addCode(asWriteFieldsCodeBlock)
            .build()
    }

    val asType: TypeSpec by lazy {
        TypeSpec.companionObjectBuilder()
            .addSuperinterface(WorkspaceConstants.KotlinConstants.tmicnPacketIoInterface.parameterizedBy(model.asClassName))
            .addFunction(asReadFunc)
            .addFunction(asWriteFunc)
            .build()
    }
}