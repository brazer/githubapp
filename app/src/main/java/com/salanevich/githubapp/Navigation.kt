package com.salanevich.githubapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salanevich.githubapp.ui.screen.detail.DetailScreen
import com.salanevich.githubapp.ui.screen.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.getDestination()) {
        composable(Screen.Main.getDestination()) {
            MainScreen { login ->
                navController.navigate(Screen.Detail.getNavigation(login))
            }
        }
        composable(
            Screen.Detail.getDestination(),
            arguments = listOf(navArgument(Screen.Detail.LOGIN_ARGS0) { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments!!.getString(Screen.Detail.LOGIN_ARGS0)
            DetailScreen(checkNotNull(url))
        }
    }
}

sealed interface Screen {

    data object Main: Screen {
        override val name: String = "main"
    }

    data object Detail: Screen {

        const val LOGIN_ARGS0 = "login"

        override val name: String = "detail"

        override fun getDestination(): String {
            return "$name/{$LOGIN_ARGS0}"
        }

        override fun getNavigation(vararg args: String): String {
            return "$name/${args[0]}"
        }
    }

    val name: String
    fun getDestination(): String = name
    fun getNavigation(vararg args: String): String = name

}