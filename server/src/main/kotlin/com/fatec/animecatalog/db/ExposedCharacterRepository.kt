package com.fatec.animecatalog.db

import com.fatec.animecatalog.model.Character
import com.fatec.animecatalog.repository.CharacterRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCharacterRepository : CharacterRepository {

    private fun ResultRow.toCharacter() = Character(
        id = this[Characters.id].value,
        animeId = this[Characters.animeId].value,
        name = this[Characters.name],
        role = this[Characters.role],
        power = this[Characters.power]
    )

    override suspend fun getAll(): List<Character> = newSuspendedTransaction {
        Characters.selectAll().map { it.toCharacter() }
    }

    override suspend fun getById(id: Int): Character? = newSuspendedTransaction {
        Characters.selectAll()
            .where { Characters.id eq id }
            .map { it.toCharacter() }
            .singleOrNull()
    }

    override suspend fun create(character: Character): Character = newSuspendedTransaction {
        val insertStatement = Characters.insert {
            it[animeId] = character.animeId
            it[name] = character.name
            it[role] = character.role
            it[power] = character.power
        }
        insertStatement.resultedValues!!.first().toCharacter()
    }

    override suspend fun update(id: Int, character: Character): Character = newSuspendedTransaction {
        Characters.update({ Characters.id eq id }) {
            it[animeId] = character.animeId
            it[name] = character.name
            it[role] = character.role
            it[power] = character.power
        }

        Characters.selectAll()
            .where { Characters.id eq id }
            .map { it.toCharacter() }
            .single()
    }

    override suspend fun delete(id: Int) {
        newSuspendedTransaction {
            Characters.deleteWhere { Characters.id eq id }
        }
    }
}