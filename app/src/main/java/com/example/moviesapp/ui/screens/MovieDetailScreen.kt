package com.example.moviesapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.remote.Error
import com.example.domain.models.Movie
import com.example.moviesapp.ui.viewmodel.MovieDetailViewModel
import com.example.moviesapp.ui.viewmodel.MovieListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailScreen(viewmodel: MovieDetailViewModel = koinViewModel(), movieId: Int){
    val state by viewmodel.state.collectAsState()

    state.detailMovie.fold(
        { error -> HandleFailure(error) },
        { movie -> MovieDetailComponent(movie) }
    )
}

@Composable
fun MovieDetailComponent(movie: Movie) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = movie.backdropPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HandleFailure(error: Error){
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