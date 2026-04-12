package com.fatec.animecatalog.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Animes : IntIdTable("animes") {
    val title = text("title")
    val genre = text("genre")
    val studio = text("studio")
    val episodes = integer("episodes")
    val releaseYear = integer("release_year")
}

object Characters : IntIdTable("characters") {
    val animeId = reference("anime_id", Animes, onDelete = ReferenceOption.CASCADE)
    val name = text("name")
    val role = text("role")
    val power = text("power").nullable()
}