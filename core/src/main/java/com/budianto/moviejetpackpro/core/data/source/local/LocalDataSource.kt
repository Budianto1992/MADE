package com.budianto.moviejetpackpro.core.data.source.local

import com.budianto.moviejetpackpro.core.data.source.local.entity.*
import com.budianto.moviejetpackpro.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow


class LocalDataSource constructor(private val mMovieDao: MovieDao) {




    fun getAllMovies(): Flow<List<MovieEntity>> = mMovieDao.getAllMovies()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = mMovieDao.getMovieFavorite()

    suspend fun insertMovie(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    suspend fun updateMovie(movie: MovieEntity) = mMovieDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isMovieFavorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }

    fun getDetailMovie(movieId: Int) = mMovieDao.getDetailMovie(movieId)

}