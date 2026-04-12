package com.fatec.animecatalog

import com.fatec.animecatalog.db.DatabaseFactory
import com.fatec.animecatalog.db.ExposedAnimeRepository
import com.fatec.animecatalog.db.ExposedCharacterRepository
import com.fatec.animecatalog.routes.animeRoutes
import com.fatec.animecatalog.routes.characterRoutes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    install(ContentNegotiation) {
        json()
    }

    val animeRepository = ExposedAnimeRepository()
    val characterRepository = ExposedCharacterRepository()

    routing {
        get("/") {
            call.respondText("Anime Catalog API ativa.")
        }

        animeRoutes(animeRepository)
        characterRoutes(characterRepository)
    }
}