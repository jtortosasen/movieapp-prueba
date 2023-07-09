package com.example.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.right
import com.example.data.remote.Error
import com.example.data.usecases.MoviesUseCase
import com.example.domain.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MovieListViewModel(private val useCase: MoviesUseCase) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val upcomingMovies: Either<Error, List<Movie>> = emptyList<Movie>().right()
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val movies = useCase.getPopularMovies()
            Log.d("debug_mode", movies.toString())
            _state.value = UiState(upcomingMovies = movies, loading = false)
        }
    }
}