package com.example.kmm_hello_world

import io.ktor.client.*
import io.ktor.client.request.*

interface UsersApi {
    suspend fun fetchUsers(): List<UserDto>
}

class UsersApiImpl(
    private val httpClient: HttpClient,
) : UsersApi {
    override suspend fun fetchUsers(): List<UserDto> =
        httpClient.get("https://jsonplaceholder.typicode.com/users")
}