package com.budianto.moviejetpackpro.di

import com.budianto.moviejetpackpro.core.domain.usecase.MovieInteractor
import com.budianto.moviejetpackpro.core.domain.usecase.MovieUseCase
import com.budianto.moviejetpackpro.ui.detail.DetailMovieViewModel
import com.budianto.moviejetpackpro.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}


val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}