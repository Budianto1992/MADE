package com.budianto.moviejetpackpro.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.budianto.moviejetpackpro.core.domain.model.Movie
import com.budianto.moviejetpackpro.core.domain.usecase.MovieUseCase


class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun detailMovie(movieId: Int) = movieUseCase.getMDetailMovie(movieId).asLiveData()

    fun setFavoriteMovie(movie: Movie, newState: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newState)
}