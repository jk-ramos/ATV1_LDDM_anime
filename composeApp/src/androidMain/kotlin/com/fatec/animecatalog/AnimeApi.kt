package com.fatec.animecatalog

import com.fatec.animecatalog.model.Anime
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object AnimeApi {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private const val BASE_URL = "http://192.168.0.100:8080"

    suspend fun getAnimes(): List<Anime> {
        return client.get("$BASE_URL/animes").body()
    }
}