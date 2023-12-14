package com.example.data.di

import com.example.data.remote.ApiService
import com.example.domain.usecases.MoviesUseCase
import org.koin.dsl.module

val dataModule = module {
    single { ApiService() }
    single { MoviesUseCase(get()) }
}