package com.example.kmm_hello_world

class GetUsersUseCase(
    private val usersApi: UsersApi,
) : suspend () -> List<User> {

    override suspend fun invoke(): List<User> =
        usersApi.fetchUsers().map(UserDto::toUser)
}