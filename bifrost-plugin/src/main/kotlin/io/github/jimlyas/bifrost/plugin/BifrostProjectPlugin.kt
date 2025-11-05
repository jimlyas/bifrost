package io.github.jimlyas.bifrost.plugin

import io.github.jimlyas.bifrost.utilities.Constants.ACTIVE_CATALOG_NAME
import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_PROPERTY
import io.github.jimlyas.bifrost.utilities.DependencyExtension.getDependencyName
import io.github.jimlyas.bifrost.utilities.DependencyExtension.substitutionReason
import io.github.jimlyas.bifrost.utilities.DependencyExtension.toProjectPath
import io.github.jimlyas.bifrost.utilities.VersionCatalogExtension.bifrostCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.component.ModuleComponentSelector

internal class BifrostProjectPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val isGateOpened = try {
            target.providers.gradleProperty(BIFROST_PROPERTY).get().toBoolean()
        } catch (_: Throwable) {
            false
        }

        target.configurations.configureEach { config ->
            config.resolutionStrategy.dependencySubstitution { substitutions ->
                val catalog = target.bifrostCatalog
                val active = catalog.findBundle(ACTIVE_CATALOG_NAME).get().get()

                substitutions.all { rule ->
                    val dependency = rule.requested
                    if (dependency is ModuleComponentSelector) {
                        val dependencyName = dependency.getDependencyName()
                        val isBifrostDependency = catalog.findLibrary(dependencyName).isPresent

                        val shouldSubstitute = when {
                            // Exclude non-bifrost dependency
                            isBifrostDependency.not() -> false

                            // Include all bifrost dependency when gate is closed
                            isGateOpened.not() -> true

                            // Include only dependency that currently active
                            else -> active.any { currentActive ->
                                currentActive.group == dependency.group && currentActive.name == dependencyName
                            }
                        }

                        if (shouldSubstitute) rule.useTarget(
                            target.rootProject.project(dependencyName.toProjectPath()),
                            dependencyName.substitutionReason()
                        )
                    }
                }
            }
        }
    }
}