import io.github.jimlyas.bifrost.extension.BifrostSettingExtension

rootProject.name = "bifrost"

pluginManagement {
    includeBuild("bifrost-plugin")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    rulesMode.set(RulesMode.FAIL_ON_PROJECT_RULES)
    repositories {
        mavenCentral()
        google()
        maven("${rootDir}/libs") {
            content {
                includeGroup("io.github.jimlyas")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("io.github.jimlyas.bifrost.settings")
}

configure<BifrostSettingExtension> {
    includeModule(":sample-a", ":sample-b", ":sample-c")
}