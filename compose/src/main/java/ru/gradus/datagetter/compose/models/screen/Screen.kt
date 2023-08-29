package ru.gradus.datagetter.compose.models.screen

sealed class Screen(val route: String) {
    open val scheme = route

    object Main : Screen("main")

    object Details : Screen("details") {
        override val scheme = "$route/{deviceId}"
        fun getRoute(deviceId: Int) = "$route/$deviceId"
    }
}
