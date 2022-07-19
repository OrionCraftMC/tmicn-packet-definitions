package io.github.orioncraftmc.tmicn.definitions.workspace.definitions

import com.fasterxml.jackson.annotation.JsonProperty

data class TmicnProtocolType(
    override val name: String,
    override val documentation: String,
    @JsonProperty("java_name") val javaName: String? = null,
    @JsonProperty("io_class") val ioClass: String? = null,
) : Documentable