package com.example.kmm_hello_world

class GetUsersUseCase : suspend () -> List<User> {

    override suspend fun invoke(): List<User> =
        UsersApi.fetchUsers().map(UserDto::toUser)
}