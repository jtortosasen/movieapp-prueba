package com.example.data.usecases

import android.util.Log
import arrow.core.Either
import arrow.core.flatMap
import com.example.data.remote.ApiService
import com.example.data.remote.Error
import com.example.data.remote.mapToDomain
import com.example.domain.models.Movie

class MoviesUseCase(private val api: ApiService) {

    private var cachedMovies: Either<Error, List<Movie>>? = null

     suspend fun getPopularMovies(): Either<Error, List<Movie>> {
         cachedMovies?.let { return it }
         return when(val eitherResult = api.receivePopularMovies()) {
             is Either.Right -> {
                 val resultDomain = eitherResult.value.results.mapIndexed() { index, movieDto ->  movieDto.mapToDomain(index) }
                 Either.Right(resultDomain).also { cachedMovies = it  }
             }
             is Either.Left -> Either.Left(eitherResult.value)
         }
    }

    fun getMovieDetails(movieId: Int): Either<Error, Movie> {
        return cachedMovies?.let { moviesEither ->
            moviesEither.flatMap { movies ->
                movies.find { it.id == movieId }
                    ?.let { Either.Right(it) }
                    ?: Either.Left(Error.Unknown("Película no encontrada"))
            }
        } ?: Either.Left(Error.Unknown("Película no encontrada"))

    }
}