package com.budianto.moviejetpackpro.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.budianto.moviejetpackpro.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getAllMovies().asLiveData()
}