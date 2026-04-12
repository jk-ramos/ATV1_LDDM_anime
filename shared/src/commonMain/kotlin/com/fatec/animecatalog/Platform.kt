package com.fatec.animecatalog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform