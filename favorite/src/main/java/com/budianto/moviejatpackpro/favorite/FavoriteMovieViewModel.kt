package com.budianto.moviejatpackpro.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.budianto.moviejetpackpro.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(useCaseMovie: MovieUseCase) : ViewModel(){
    val favoriteMovie = useCaseMovie.getFavoriteMovie().asLiveData()
}