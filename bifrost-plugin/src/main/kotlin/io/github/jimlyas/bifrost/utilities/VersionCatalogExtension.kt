package io.github.jimlyas.bifrost.utilities

import io.github.jimlyas.bifrost.utilities.Constants.BIFROST_CATALOG_NAME
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

internal object VersionCatalogExtension {

    val Project.bifrostCatalog
        get() : VersionCatalog = extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named(BIFROST_CATALOG_NAME)

}