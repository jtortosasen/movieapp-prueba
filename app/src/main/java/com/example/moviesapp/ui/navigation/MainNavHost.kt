package com.example.moviesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.screens.MovieListScreen
import com.example.moviesapp.ui.screens.MovieDetailScreen
import com.example.moviesapp.ui.navigation.Screen.Detail
import com.example.moviesapp.ui.viewmodel.MovieDetailViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object Screen {
    const val Overview = "overview"
    const val Detail = "detail/{itemId}"
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Overview) {
        composable(Screen.Overview) {
            MovieListScreen(onItemClick = { itemId ->
                navController.navigate(Detail.replace("{itemId}", itemId.toString()))
            })
        }
        composable(Detail) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toInt()
            val viewModel = getViewModel<MovieDetailViewModel>(parameters = { parametersOf(itemId) })
            MovieDetailScreen(viewmodel = viewModel, movieId = itemId!!)
        }
    }
}