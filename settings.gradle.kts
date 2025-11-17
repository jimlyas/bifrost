import io.github.jimlyas.bifrost.extension.BifrostSettingExtension

rootProject.name = "bifrost"

pluginManagement {
    includeBuild("bifrost-plugin")
//    resolutionStrategy.eachPlugin {
//        if (requested.id.id == "io.github.jimlyas.bifrost") useModule(
//            "io.github.jimlyas:bifrost:${requested.version}"
//        )
//    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
//        maven("${rootDir}/libs") {
//            content {
//                includeGroup("io.github.jimlyas")
//            }
//        }
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
//    id("io.github.jimlyas.bifrost") version "0.1.0-SNAPSHOT"
    id("io.github.jimlyas.bifrost")
}

configure<BifrostSettingExtension> {
    includeModule(":sample-a", ":sample-b", ":sample-c")
}