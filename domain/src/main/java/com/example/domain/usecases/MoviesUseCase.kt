package com.example.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.example.domain.data.IApiService
import com.example.domain.models.Movie
import com.example.domain.models.Error

class MoviesUseCase(private val api: IApiService) {

    private var cachedMovies: Either<Error, List<Movie>>? = null

     suspend fun getPopularMovies(): Either<Error, List<Movie>> {
         cachedMovies?.let { return it }
         return when(val eitherResult = api.receivePopularMovies()) {
             is Either.Right -> {
                 val result = eitherResult.value
                 Either.Right(result).also { cachedMovies = it  }
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