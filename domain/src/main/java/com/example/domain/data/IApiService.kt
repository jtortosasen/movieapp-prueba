package com.example.domain.data

import arrow.core.Either
import com.example.domain.models.Movie
import com.example.domain.models.Error

interface IApiService {
    suspend fun receivePopularMovies(): Either<Error, List<Movie>>
}