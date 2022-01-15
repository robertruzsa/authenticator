package com.robertruzsa.authenticator.ui.navigation

sealed class Screen(val route: String) {
    object List : Screen("list")
    object NewItem : Screen("new_item")
}
