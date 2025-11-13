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
        maven("../libs") {
            name = "localRepo"
        }
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
            groupId = project.group.toString()
            artifactId = "bifrost"
            version = currentVersion
            from(components["java"])
            pom {
                name.set("Bifrost")
                description.set("A lightweight, simple, and useful Gradle Plugin to boost your productivity!")
                url.set("https://jimlyas.github.io/bifrost")
                scm {
                    url.set("https://github.com/jimlyas/bifrost")
                    connection.set("scm:git:git://github.com/jimlyas/bifrost.git")
                    developerConnection.set("scm:git:ssh://git@github.com/jimlyas/bifrost.git")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://mit-license.org/")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("jimlyas")
                        name.set("Jimly Asshiddiqy")
                        email.set("j_mly@ymail.com")
                        url.set("https://github.com/jimlyas")
                        roles.set(listOf("maintainer"))
                        timezone.set("+7")
                    }
                }
                issueManagement {
                    url.set("https://github.com/jimlyas/bifrost/issues")
                    system.set("GitHub Issues")
                }
            }
        }
    }
}

gradlePlugin {
    website.set("https://jimlyas.github.io/bifrost")
    vcsUrl.set("https://github.com/jimlyas/bifrost")

    plugins {
        register("io.github.jimlyas.bifrost") {
            id = "io.github.jimlyas.bifrost"
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