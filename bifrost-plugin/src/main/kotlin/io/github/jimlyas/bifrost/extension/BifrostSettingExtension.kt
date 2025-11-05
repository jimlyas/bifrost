package io.github.jimlyas.bifrost.extension

import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_CATALOG_DIRECTORY

abstract class BifrostSettingExtension : SettingBaseExtension {

    init {
        bifrostCatalog.convention(BIFROST_CATALOG_DIRECTORY)
    }

    override val modules: MutableList<String> = mutableListOf()

    override fun includeModule(vararg module: String) {
        modules.addAll(module)
    }

    override fun includeModule(module: String) {
        modules.add(module)
    }
}