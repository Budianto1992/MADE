package com.budianto.moviejetpackpro.core.data.source.local.room

import androidx.room.*
import com.budianto.moviejetpackpro.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentities")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentities where isMovieFavorite = 1")
    fun getMovieFavorite(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: MovieEntity)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getDetailMovie(movieId: Int): Flow<MovieEntity>
}