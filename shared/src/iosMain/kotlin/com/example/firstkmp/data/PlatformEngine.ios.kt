package com.example.firstkmp.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getPlatformEngine(): HttpClientEngine = Darwin.create()