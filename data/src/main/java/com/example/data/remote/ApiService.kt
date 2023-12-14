package com.example.data.remote

import android.util.Log
import arrow.core.Either
import com.example.data.models.MovieResultDto
import com.example.data.models.mapToDomain
import com.example.domain.data.IApiService
import com.example.domain.models.Error
import com.example.domain.models.Movie
import com.example.domain.models.toError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ApiService: IApiService {
    private val bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZGVjZWYyNWMzMzY2NTYyNmRhZDE5NzQ5ZWZlNDVhYSIsInN1YiI6IjY1N2ExMmE1NTY0ZWM3MDBjNDc0Y2I3YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.t9R1cqyQov8WLiZgOiZji-pS62SeJRRRLLo-Rh-gon0"
    private val client = HttpClient() {
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override suspend fun receivePopularMovies(): Either<Error, List<Movie>> {
        return try {
            val popularMoviesList: MovieResultDto = client.get(ApiEndpoints.baseUrl + ApiEndpoints.popularity) {
                header("Authorization", bearerToken)
            }.body()
            Log.d("debug_mode", popularMoviesList.toString())
            Either.Right(popularMoviesList.results.mapIndexed { index, movieDto ->  movieDto.mapToDomain(index) })
        } catch (e: Exception) {
            Log.e("debug_mode", "Error occurred", e)
            Either.Left(e.toError())
        }
    }
}
