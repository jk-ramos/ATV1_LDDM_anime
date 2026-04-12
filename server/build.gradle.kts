plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.3.20"
    application
}

application {
    mainClass.set("com.fatec.animecatalog.ApplicationKt")
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.logback)

    implementation(libs.ktorServerCore)
    implementation(libs.ktorServerNetty)
    implementation(libs.ktorServerContentNegotiation)
    implementation(libs.ktorSerializationKotlinxJson)
    implementation(libs.ktorServerSwagger)
    implementation(libs.ktorServerStatusPages)

    implementation(libs.exposedCore)
    implementation(libs.exposedDao)
    implementation(libs.exposedJdbc)

    implementation(libs.flywayCore)
    implementation(libs.flywayDatabasePostgresql)
    implementation(libs.postgresql)

    implementation(libs.kotlinxSerializationJson)
    implementation(libs.kotlinxCoroutinesCore)

    testImplementation(libs.ktorServerTestHost)
    testImplementation(libs.kotlinTest)
}