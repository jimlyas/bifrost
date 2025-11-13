plugins {
    alias(libs.plugins.kotlin) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}