package io.github.orioncraftmc.tmicn.definitions.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Task

abstract class TmicnTask : DefaultTask() {
    init {
        group = "tmicn"
    }

}