package com.example.kmm_hello_world.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.kmm_hello_world.UsersModule

class MainActivity : AppCompatActivity() {
    private val usersModule = UsersModule()
    private val usersStateStore = usersModule.usersStateStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HelloWorld(usersStateStore) }
    }
}
