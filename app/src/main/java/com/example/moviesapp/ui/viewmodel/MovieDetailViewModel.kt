package com.example.moviesapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.data.remote.Error
import com.example.data.usecases.MoviesUseCase
import com.example.domain.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieId: Int, private val useCase: MoviesUseCase) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val detailMovie: Either<Error, Movie> = Either.Left(Error.Unknown("Película no encontrada"))
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val movie = useCase.getMovieDetails(movieId)
            _state.value = UiState(detailMovie = movie, loading = false)
        }
    }
}