package com.example.kmm_hello_world.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.kmm_hello_world.GetUsersUseCase
import com.example.kmm_hello_world.UsersStateStore

class MainActivity : AppCompatActivity() {
    private val usersStateStore = UsersStateStore(GetUsersUseCase())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HelloWorld(usersStateStore) }
    }
}
