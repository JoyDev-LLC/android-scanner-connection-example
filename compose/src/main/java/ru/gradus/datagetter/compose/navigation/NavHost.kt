package ru.gradus.datagetter.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.gradus.datagetter.compose.models.screen.Screen
import ru.gradus.datagetter.compose.ui.screens.details.Details
import ru.gradus.datagetter.compose.ui.screens.main.MainScreen

@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Main.route
) {

    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
        composable(Screen.Main.scheme) {
            MainScreen(navController = navController)
        }
        composable(Screen.Details.scheme) { backStackEntry ->
            Details(navController = navController, deviceId = backStackEntry.arguments?.getString("deviceId")?.toInt() ?: 0)
        }
    }
}
