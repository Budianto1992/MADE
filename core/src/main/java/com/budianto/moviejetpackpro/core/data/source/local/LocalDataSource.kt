package com.budianto.moviejetpackpro.core.data.source.local

import com.budianto.moviejetpackpro.core.data.source.local.entity.*
import com.budianto.moviejetpackpro.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow


class LocalDataSource constructor(private val mMovieDao: MovieDao) {

//    companion object{
//        private var INSTANCE: LocalDataSource? = null
//
//        fun getInstance(movieDao: MovieDao, tvShowDao: TvShowDao) : LocalDataSource{
//            if (INSTANCE == null){
//                INSTANCE = LocalDataSource(movieDao, tvShowDao)
//            }
//            return INSTANCE as LocalDataSource
//        }
//    }


    fun getAllMovies(): Flow<List<MovieEntity>> = mMovieDao.getAllMovies()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = mMovieDao.getMovieFavorite()

    suspend fun insertMovie(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    suspend fun updateMovie(movie: MovieEntity) = mMovieDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isMovieFavorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }
//
//    suspend fun cleareFavoriteMovie() = mMovieDao.clearFavoriteMovie()

    fun getDetailMovie(movieId: Int) = mMovieDao.getDetailMovie(movieId)

}