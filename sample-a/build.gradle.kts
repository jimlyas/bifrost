import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    bifrost.project
    `maven-publish`
}

group = "io.github.jimlyas"
version = "0.1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

publishing {
    publications {
        register<MavenPublication>("sample") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }

    repositories {
        maven(uri(rootProject.layout.projectDirectory.file("libs").toString())) {
            name = "local"
        }
    }
}

dependencies {
    implementation(realm.sample.b)
    implementation(realm.sample.c)
    testImplementation(kotlin("test"))
}