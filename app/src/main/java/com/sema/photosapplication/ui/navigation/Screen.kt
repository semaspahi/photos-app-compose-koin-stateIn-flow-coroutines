package com.sema.photosapplication.ui.navigation

sealed class Screen(val route: String) {
    object Albums : Screen("albums")
    object Photos : Screen("albums/{id}") {
        fun createRoute(id: Int) = "albums/$id"
    }
    object PhotoDetail : Screen("photo/{id}") {
        fun createRoute(id: Int) = "photo/$id"
    }
}