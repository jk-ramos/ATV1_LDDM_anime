package com.fatec.animecatalog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int = 0,
    @SerialName("anime_id")
    val animeId: Int,
    val name: String,
    val role: String,
    val power: String? = null
)