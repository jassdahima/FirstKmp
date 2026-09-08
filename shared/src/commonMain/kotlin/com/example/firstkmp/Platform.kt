package com.example.firstkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform