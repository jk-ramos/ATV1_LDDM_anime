package com.fatec.animecatalog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Anime(
    val id: Int = 0,
    val title: String,
    val genre: String,
    val studio: String,
    val episodes: Int,
    @SerialName("release_year")
    val releaseYear: Int
)