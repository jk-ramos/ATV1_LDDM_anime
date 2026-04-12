package com.fatec.animecatalog.routes

import com.fatec.animecatalog.model.Character
import com.fatec.animecatalog.repository.CharacterRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.characterRoutes(characterRepository: CharacterRepository) {

    get("/characters") {
        call.respond(characterRepository.getAll())
    }

    post("/characters") {
        try {
            val character = call.receive<Character>()
            val created = characterRepository.create(character)
            call.respond(HttpStatusCode.Created, created)
        } catch (_: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Formato de personagem inválido")
            )
        }
    }

    put("/characters/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

        try {
            val character = call.receive<Character>()
            val updated = characterRepository.update(id, character)
            call.respond(HttpStatusCode.OK, updated)
        } catch (_: Exception) {
            call.respond(
                HttpStatusCode.NotFound,
                mapOf("error" to "Personagem não encontrado")
            )
        }
    }

    delete("/characters/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

        characterRepository.delete(id)
        call.respond(HttpStatusCode.NoContent)
    }
}