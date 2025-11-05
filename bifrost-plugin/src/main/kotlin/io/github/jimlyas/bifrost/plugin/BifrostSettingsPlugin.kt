package io.github.jimlyas.bifrost.plugin

import io.github.jimlyas.bifrost.extension.BifrostSettingExtension
import io.github.jimlyas.bifrost.model.CatalogEntries
import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_CATALOG_NAME
import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_PROPERTY
import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_SETTING_EXTENSION_NAME
import kotlinx.serialization.decodeFromString
import net.peanuuutz.tomlkt.Toml
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import java.nio.file.Paths
import kotlin.io.path.readText

internal class BifrostSettingsPlugin : Plugin<Settings> {

    @Suppress("UnstableApiUsage")
    override fun apply(target: Settings) {
        val settingExtension = target.extensions.create(
            BIFROST_SETTING_EXTENSION_NAME,
            BifrostSettingExtension::class.java
        )

        target.dependencyResolutionManagement
            .versionCatalogs
            .register(BIFROST_CATALOG_NAME) { builder ->
                builder.from(
                    target.layout.rootDirectory.files(
                        settingExtension.bifrostCatalog.get()
                    )
                )
            }

        // Variable to define which dependency to use, is pre-built artifact or module dependency?
        val isGateOpened = try {
            target.providers.gradleProperty(BIFROST_PROPERTY).get().toBoolean()
        } catch (_: Throwable) {
            println("There's no '${BIFROST_PROPERTY}' gradle property defined, defaulting the value to false")
            false
        }

        target.gradle.settingsEvaluated {
            target.include(
                if (isGateOpened) Toml {
                    ignoreUnknownKeys = true
                }.decodeFromString<CatalogEntries>(
                    Paths.get(settingExtension.bifrostCatalog.get()).readText()
                ).bundles.active.map { module -> ":$module" }
                else settingExtension.modules
            )
        }
    }

}