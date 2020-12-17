package com.budianto.moviejatpackpro.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val favoriteMovieModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
}