package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.Either
import com.example.domain.models.Movie
import com.example.moviesapp.ui.components.MoviePoster
import com.example.moviesapp.ui.viewmodel.MovieListViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.domain.models.Error


@Composable
fun MovieListScreen(viewmodel: MovieListViewModel = koinViewModel(), onItemClick: (Int) -> Unit) {
    val state by viewmodel.state.collectAsState()
    MovieListComponent(loading = state.loading, items = state.upcomingMovies, onClick = onItemClick)
}

@Composable
fun MovieListComponent(loading: Boolean, items: Either<Error, List<Movie>>, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            items.fold(
                { error -> MovieListErrorState(error) },
                { movies -> MovieListSuccessState(movies, onClick) }
            )
        }
    }
}

@Composable
fun MovieListSuccessState(items: List<Movie>, onClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items) {
            MoviePoster(it, onClick)
        }
    }
}

@Composable
fun MovieListErrorState(error: Error){
    when (error) {
        is Error.Server -> {
            Text(text = "Server error with code: ${error.code}")
        }
        is Error.Connectivity -> {
            Text(text = "Connectivity error")
        }
        is Error.Unknown -> {
            Text(text = "Unknown error: ${error.message}")
        }
    }
}