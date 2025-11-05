package io.github.jimlyas.bifrost.utilities

import org.gradle.api.artifacts.component.ModuleComponentSelector

internal object DependencyExtension {

    fun String.toProjectPath(): String = ":$this"

    fun String.substitutionReason() = "$this dependency substituted with local :$this module"

    fun ModuleComponentSelector.getDependencyName(): String = displayName.substringAfter(":").substringBefore(":")
}