package com.example.moviesapp.di

import com.example.moviesapp.ui.viewmodel.MovieDetailViewModel
import com.example.moviesapp.ui.viewmodel.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { parameters -> MovieDetailViewModel(movieId = parameters.get(), get()) }
}