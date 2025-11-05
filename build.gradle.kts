plugins {
    alias(libs.plugins.kotlin) apply false
    bifrost.project apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}