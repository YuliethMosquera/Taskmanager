package com.example.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmanager.ui.screen.tasklist.TaskListScreen
import com.example.taskmanager.ui.screen.tasklist.TaskViewModel
import com.example.taskmanager.ui.screens.drafts.DraftsScreen
import com.example.taskmanager.ui.screens.login.LoginScreen
import com.example.taskmanager.ui.screens.register.RegisterScreen
import com.example.taskmanager.ui.screens.taskform.TaskFormScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.TaskList.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            TaskListScreen(viewModel = viewModel)
        }

        composable(Screen.Drafts.route) {
            DraftsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.TaskForm.route) {
            TaskFormScreen(
                onBackClick = { navController.popBackStack() },
                onTaskSaved = { navController.popBackStack() }
            )
        }
    }
}