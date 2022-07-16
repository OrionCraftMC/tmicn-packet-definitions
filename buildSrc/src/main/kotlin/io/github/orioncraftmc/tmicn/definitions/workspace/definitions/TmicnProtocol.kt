package io.github.orioncraftmc.tmicn.definitions.workspace.definitions

data class TmicnProtocol(
    override val name: String,
    override val documentation: String,
    val types: MutableList<TmicnProtocolType> = mutableListOf()
) : Documentable

