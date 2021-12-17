package com.example.kmm_hello_world

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

val httpClient = HttpClient {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 60_000
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer(
            kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}