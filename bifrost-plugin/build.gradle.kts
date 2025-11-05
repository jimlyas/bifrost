@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.gradle.publish)
    `java-gradle-plugin`
}

val currentVersion = "0.1.0"

group = "io.github.jimlyas"
version = currentVersion

gradlePlugin {
    website.set("https://github.com/jimlyas/bifrost")
    vcsUrl.set("https://github.com/jimlyas/bifrost")

    plugins {
        register("io.github.jimlyas.bifrost.settings") {
            id = "io.github.jimlyas.bifrost.settings"
            implementationClass = "io.github.jimlyas.bifrost.plugin.BifrostSettingsPlugin"
            version = currentVersion
            description = "Bifrost Gradle Plugin for Settings Script"
            displayName = "Bifrost Settings"
            tags = listOf("module", "dependency", "management")
        }

        register("io.github.jimlyas.bifrost.project") {
            id = "io.github.jimlyas.bifrost.project"
            implementationClass = "io.github.jimlyas.bifrost.plugin.BifrostProjectPlugin"
            version = currentVersion
            description = "Bifrost Gradle Plugin for Project Build Script"
            displayName = "Bifrost Project"
            tags = listOf("module", "dependency", "management")
        }
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

dependencies {
    implementation(libs.tomlkt)
}