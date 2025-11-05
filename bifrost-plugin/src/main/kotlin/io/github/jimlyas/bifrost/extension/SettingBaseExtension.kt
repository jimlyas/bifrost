package io.github.jimlyas.bifrost.extension

import org.gradle.api.provider.Property

internal interface SettingBaseExtension {
    val modules: MutableList<String>

    val bifrostCatalog: Property<String>

    fun includeModule(module: String)

    fun includeModule(vararg module: String)
}