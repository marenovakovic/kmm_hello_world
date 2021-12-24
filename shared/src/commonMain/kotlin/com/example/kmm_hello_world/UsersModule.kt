package com.example.kmm_hello_world

import dev.shustoff.dikt.Create
import dev.shustoff.dikt.CreateSingle
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

class UsersModule {

    private val httpClient: HttpClient by lazy {
        HttpClient {
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
    }

    @CreateSingle
    private fun usersApi(): UsersApi

    @Create
    fun getUsersUseCase(): GetUsersUseCase

    @CreateSingle
    fun usersStateSource(): UsersStateStore
}