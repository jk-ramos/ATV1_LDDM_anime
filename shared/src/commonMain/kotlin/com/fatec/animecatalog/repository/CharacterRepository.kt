package com.fatec.animecatalog.repository

import com.fatec.animecatalog.model.Character

interface CharacterRepository {
    suspend fun getAll(): List<Character>
    suspend fun getById(id: Int): Character?
    suspend fun create(character: Character): Character
    suspend fun update(id: Int, character: Character): Character
    suspend fun delete(id: Int)
}