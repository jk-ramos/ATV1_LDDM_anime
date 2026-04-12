package com.fatec.animecatalog.db

import com.fatec.animecatalog.model.Anime
import com.fatec.animecatalog.repository.AnimeRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedAnimeRepository : AnimeRepository {

    private fun ResultRow.toAnime() = Anime(
        id = this[Animes.id].value,
        title = this[Animes.title],
        genre = this[Animes.genre],
        studio = this[Animes.studio],
        episodes = this[Animes.episodes],
        releaseYear = this[Animes.releaseYear]
    )

    override suspend fun getAll(): List<Anime> = newSuspendedTransaction {
        Animes.selectAll().map { it.toAnime() }
    }

    override suspend fun getById(id: Int): Anime? = newSuspendedTransaction {
        Animes.selectAll()
            .where { Animes.id eq id }
            .map { it.toAnime() }
            .singleOrNull()
    }

    override suspend fun create(anime: Anime): Anime = newSuspendedTransaction {
        val insertStatement = Animes.insert {
            it[title] = anime.title
            it[genre] = anime.genre
            it[studio] = anime.studio
            it[episodes] = anime.episodes
            it[releaseYear] = anime.releaseYear
        }
        insertStatement.resultedValues!!.first().toAnime()
    }

    override suspend fun update(id: Int, anime: Anime): Anime = newSuspendedTransaction {
        Animes.update({ Animes.id eq id }) {
            it[title] = anime.title
            it[genre] = anime.genre
            it[studio] = anime.studio
            it[episodes] = anime.episodes
            it[releaseYear] = anime.releaseYear
        }

        Animes.selectAll()
            .where { Animes.id eq id }
            .map { it.toAnime() }
            .single()
    }

    override suspend fun delete(id: Int) {
        newSuspendedTransaction {
            Animes.deleteWhere { Animes.id eq id }
        }
    }
}