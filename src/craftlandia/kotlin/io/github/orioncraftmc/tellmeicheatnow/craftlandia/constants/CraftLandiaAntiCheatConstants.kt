package io.github.orioncraftmc.tellmeicheatnow.craftlandia.constants

data class CraftLandiaAntiCheatConstants(
    val antiHackVersion: String /* AntiHackVersion#version */,

    val antiHackPayloadChannel: String /* VersionHandler#versionChannelName */,
    val clientJarHash: String,
    val antiHackReplyId: String /* VersionHandler#versionId */,

    val clientData: CraftLandiaClientDataConstants
)