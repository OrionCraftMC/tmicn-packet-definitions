package io.github.orioncraftmc.tmicn.definitions.outputters

import io.github.orioncraftmc.tmicn.definitions.workspace.ProtocolDefinition
import java.nio.file.Path

interface Outputter {
    fun output(definition: ProtocolDefinition, output: Path)
}