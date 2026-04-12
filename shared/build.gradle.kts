
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "2.3.20"
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinxSerializationJson)
            implementation(libs.kotlinxCoroutinesCore)
        }
    }
}

android {
    namespace = "com.fatec.animecatalog.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}