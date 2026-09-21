package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.domain.usecase.GetCurrentUserUseCase
import com.example.taskmanager.navigation.Screen
import com.example.taskmanager.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = if (getCurrentUserUseCase() != null) {
            Screen.TaskList.route
        } else {
            Screen.Login.route
        }

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}