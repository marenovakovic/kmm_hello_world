package com.example.kmm_hello_world

fun UserDto.toUser() = User(id, name, username, email)