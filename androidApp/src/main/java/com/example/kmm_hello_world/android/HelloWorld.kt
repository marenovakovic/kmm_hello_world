package com.example.kmm_hello_world.android

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmm_hello_world.User
import com.example.kmm_hello_world.UsersState
import com.example.kmm_hello_world.UsersStateStore

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HelloWorld(usersStateStore: UsersStateStore) {
    val state by usersStateStore.state.collectAsState()

    Crossfade(modifier = Modifier.fillMaxSize(), targetState = state) { targetState ->
        when (targetState) {
            UsersState.Loading -> LoadingIndicator()
            is UsersState.Users -> Users(targetState.users)
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Users(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            User(user = user)
        }
    }
}

@Composable
private fun User(
    modifier: Modifier = Modifier,
    user: User,
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(user.name)
        Spacer(modifier = Modifier.height(4.dp))
        Text(user.username)
        Spacer(modifier = Modifier.height(4.dp))
        Text(user.email)
    }
}