package com.example.kmm_hello_world

import io.ktor.client.*
import io.ktor.client.request.*

class UsersApi(
    private val httpClient: HttpClient,
) {
    suspend fun fetchUsers(): List<UserDto> =
        httpClient.get("https://jsonplaceholder.typicode.com/users")
}