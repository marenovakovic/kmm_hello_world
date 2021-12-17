package com.example.kmm_hello_world

import io.ktor.client.request.*

object UsersApi {
    suspend fun fetchUsers(): List<UserDto> =
        httpClient.get("https://jsonplaceholder.typicode.com/users")
}