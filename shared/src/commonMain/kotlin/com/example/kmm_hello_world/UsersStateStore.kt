package com.example.kmm_hello_world

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UsersState {
    object Loading : UsersState()
    data class Users(val users: List<User>) : UsersState()
}

class UsersStateStore(
    private val getUsersUseCase: GetUsersUseCase,
) : CoroutineScope by MainScope() {

    private var _state: MutableStateFlow<UsersState> = MutableStateFlow(UsersState.Loading)
    val state: StateFlow<UsersState>
        get() = _state.asStateFlow()

    init {
        launch {
            _state.value = UsersState.Users(getUsersUseCase())
        }
    }
}