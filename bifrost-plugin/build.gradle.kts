@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.gradle.publish)
    `java-gradle-plugin`
}

val currentVersion = providers.environmentVariable("BIFROST_VERSION").getOrElse("0.1.0-SNAPSHOT")

group = "io.github.jimlyas"
version = currentVersion

publishing {
    repositories {
        maven(uri("https://maven.pkg.github.com/jimlyas/bifrost")) {
            name = "GitHubPackages"
            credentials {
                username = providers.environmentVariable("GPR_USERNAME").getOrElse("jimlyas")
                password = providers.environmentVariable("GPR_TOKEN").getOrElse("randomTokenHere")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            group = project.group
            version = currentVersion
            from(components["java"])
        }
    }
}

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
            tags = listOf("module", "dependency", "management", "settings", "artifact")
        }

        register("io.github.jimlyas.bifrost.project") {
            id = "io.github.jimlyas.bifrost.project"
            implementationClass = "io.github.jimlyas.bifrost.plugin.BifrostProjectPlugin"
            version = currentVersion
            description = "Bifrost Gradle Plugin for Project Build Script"
            displayName = "Bifrost Project"
            tags = listOf("module", "dependency", "management", "project", "artifact")
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