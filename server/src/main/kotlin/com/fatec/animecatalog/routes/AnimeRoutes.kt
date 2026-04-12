package com.fatec.animecatalog.routes

import com.fatec.animecatalog.model.Anime
import com.fatec.animecatalog.repository.AnimeRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.animeRoutes(animeRepository: AnimeRepository) {

    get("/animes") {
        call.respond(animeRepository.getAll())
    }

    post("/animes") {
        try {
            val anime = call.receive<Anime>()
            val created = animeRepository.create(anime)
            call.respond(HttpStatusCode.Created, created)
        } catch (_: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Formato de anime inválido")
            )
        }
    }

    put("/animes/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

        try {
            val anime = call.receive<Anime>()
            val updated = animeRepository.update(id, anime)
            call.respond(HttpStatusCode.OK, updated)
        } catch (_: Exception) {
            call.respond(
                HttpStatusCode.NotFound,
                mapOf("error" to "Anime não encontrado")
            )
        }
    }

    delete("/animes/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

        animeRepository.delete(id)
        call.respond(HttpStatusCode.NoContent)
    }
}