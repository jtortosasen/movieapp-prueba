package com.example.data.di

import com.example.data.remote.ApiService
import com.example.data.usecases.MoviesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiService() }
    single { MoviesUseCase(get()) }
}