package com.budianto.moviejetpackpro.core.domain.repository

import com.budianto.moviejetpackpro.core.data.Resource
import com.budianto.moviejetpackpro.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)

}