package com.example.data.remote

import android.util.Log
import arrow.core.Either
import com.example.data.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ApiService {
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

    suspend fun receivePopularMovies(): Either<Error, MovieResultDto> {
        return try {
            val popularMoviesList: MovieResultDto = client.get(ApiEndpoints.baseUrl + ApiEndpoints.popularity) {
                header("Authorization", bearerToken)
            }.body()
            Log.d("debug_mode", popularMoviesList.toString())
            Either.Right(popularMoviesList)
        } catch (e: Exception) {
            Log.e("debug_mode", "Error occurred", e)
            Either.Left(e.toError())
        }
    }
}
