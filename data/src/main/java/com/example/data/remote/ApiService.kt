package com.example.data.remote

import android.util.Log
import arrow.core.Either
import com.example.data.BuildConfig
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
    private val bearerToken = BuildConfig.BEARER_TOKEN
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
