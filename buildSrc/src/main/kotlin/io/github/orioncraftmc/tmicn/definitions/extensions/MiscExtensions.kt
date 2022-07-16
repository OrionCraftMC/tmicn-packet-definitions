package io.github.orioncraftmc.tmicn.definitions.extensions

import org.gradle.api.Project

val Project.tmicn: ProtocolDefinitionsExtension
    get() = extensions.getByType(ProtocolDefinitionsExtension::class.java)