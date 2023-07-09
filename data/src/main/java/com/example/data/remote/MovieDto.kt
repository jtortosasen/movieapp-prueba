package com.example.data.remote

import com.example.data.BuildConfig
import com.example.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResultDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class MovieDto(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

fun MovieDto.mapToDomain(index: Int): Movie {
    return Movie(
        id = index,
        adult = this.adult,
        backdropPath = (ApiEndpoints.imageBaseUrl + ApiEndpoints.imageOriginalSizeUrl + this.backdropPath + "?api_key=" + BuildConfig.API_KEY) ?: "",
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = (ApiEndpoints.imageBaseUrl + ApiEndpoints.imagePosterSizeUrl + this.posterPath + "?api_key=" + BuildConfig.API_KEY) ?: "",
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage
    )
}