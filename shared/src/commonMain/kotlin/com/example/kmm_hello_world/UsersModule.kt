package com.example.kmm_hello_world

import dev.shustoff.dikt.UseConstructors
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

@UseConstructors(UsersApiImpl::class, GetUsersUseCase::class, UsersStateStore::class)
class UsersModule {
    private val httpClient by lazy {
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

    private val usersApi: UsersApi by lazy { UsersApiImpl(httpClient) }

    private val getUsersUseCase by lazy { GetUsersUseCase(usersApi) }

    val usersStateStore by lazy { UsersStateStore(getUsersUseCase) }
}