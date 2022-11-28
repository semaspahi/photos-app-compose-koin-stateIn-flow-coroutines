package com.sema.photosapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sema.photosapplication.ui.view.albums.AlbumsScreen
import com.sema.photosapplication.ui.view.photodetail.PhotoDetailScreen
import com.sema.photosapplication.ui.view.photos.PhotosScreen


@Preview
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController, startDestination = Screen.Albums.route) {
        composable(Screen.Albums.route) { AlbumsScreen(onClick = { actions.goToPhotosScreen(it) }) }
        composable(Screen.Photos.route) {
            PhotosScreen(
                onClick = { actions.goToPhotoDetailScreen(it) },
                onBackClick = { actions.popBackStack() })
        }
        composable(Screen.PhotoDetail.route) { PhotoDetailScreen(onBackClick = { actions.popBackStack() }) }
    }
}

class MainActions(private val navController: NavHostController) {

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }
    val goToPhotosScreen: (Int) -> Unit = {
        navController.navigate(Screen.Photos.createRoute(it))
    }

    val goToPhotoDetailScreen: (Int) -> Unit = {
        navController.navigate(Screen.PhotoDetail.createRoute(it))
    }

}