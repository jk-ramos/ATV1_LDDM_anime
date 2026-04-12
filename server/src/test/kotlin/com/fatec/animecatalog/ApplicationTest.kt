package com.fatec.animecatalog.anime_atalog_kmp

import com.fatec.animecatalog.animecatalog.module
import com.fatec.com.fatec.animecatalog.anime_atalog_kmp.Greeting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Ktor: ${Greeting().greet()}", response.bodyAsText())
    }
}