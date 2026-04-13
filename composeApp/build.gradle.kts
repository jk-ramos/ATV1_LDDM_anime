import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.composeUiToolingPreview)
            implementation(libs.androidxActivityCompose)
            implementation(libs.ktorClientOkhttp)
        }

        commonMain.dependencies {
            implementation(libs.composeRuntime)
            implementation(libs.composeFoundation)
            implementation(libs.composeMaterial3)
            implementation(libs.composeUi)
            implementation(libs.composeComponentsResources)
            implementation(libs.composeUiToolingPreview)
            implementation(libs.androidxLifecycleViewmodelCompose)
            implementation(libs.androidxLifecycleRuntimeCompose)
            implementation(project(":shared"))
            implementation(libs.ktorClientCore)
            implementation(libs.ktorClientContentNegotiation)
            implementation(libs.kotlinxSerializationJson)
            implementation(libs.ktorSerializationKotlinxJson)
            implementation(compose.materialIconsExtended)
        }

        commonTest.dependencies {
            implementation(libs.kotlinTest)
        }
    }
}

android {
    namespace = "com.fatec.animecatalog"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.fatec.animecatalog"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.composeUiTooling)
}