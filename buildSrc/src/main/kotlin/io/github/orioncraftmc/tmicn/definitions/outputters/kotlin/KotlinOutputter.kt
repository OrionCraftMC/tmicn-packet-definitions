package io.github.orioncraftmc.tmicn.definitions.outputters.kotlin

import com.squareup.kotlinpoet.FileSpec
import io.github.orioncraftmc.tmicn.definitions.outputters.Outputter
import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import java.nio.file.Path

object KotlinOutputter : Outputter {
    override fun output(definition: ProtocolDefinition, output: Path) {
        val specs = mutableListOf<FileSpec>()

        specs.add(entrypoint(definition))
        definition.packets.mapTo(specs) { packet(definition, it) }


        for (spec in specs) {
            spec.writeTo(output)
        }
    }

}