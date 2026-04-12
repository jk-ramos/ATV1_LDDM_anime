package com.fatec.animecatalog.repository

import com.fatec.animecatalog.model.Anime

interface AnimeRepository {
    suspend fun getAll(): List<Anime>
    suspend fun getById(id: Int): Anime?
    suspend fun create(anime: Anime): Anime
    suspend fun update(id: Int, anime: Anime): Anime
    suspend fun delete(id: Int)
}