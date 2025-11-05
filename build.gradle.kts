plugins {
    alias(libs.plugins.kotlin) apply false
    id("io.github.jimlyas.bifrost.project") apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}