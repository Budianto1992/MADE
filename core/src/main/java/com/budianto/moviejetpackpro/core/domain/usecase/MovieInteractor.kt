package com.budianto.moviejetpackpro.core.domain.usecase

import com.budianto.moviejetpackpro.core.data.Resource
import com.budianto.moviejetpackpro.core.domain.model.Movie
import com.budianto.moviejetpackpro.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase{

    override fun getAllMovies() = movieRepository.getAllMovies()
    override fun getMDetailMovie(movieId: Int): Flow<Resource<Movie>> = movieRepository.getDetailMovie(movieId)
    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()
    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}