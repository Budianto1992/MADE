package com.budianto.moviejetpackpro.core.data

import com.budianto.moviejetpackpro.core.data.source.local.LocalDataSource
import com.budianto.moviejetpackpro.core.data.source.remote.network.ApiResponse
import com.budianto.moviejetpackpro.core.data.source.remote.RemoteDataSource
import com.budianto.moviejetpackpro.core.data.source.remote.remotemovie.MovieResponse
import com.budianto.moviejetpackpro.core.domain.model.Movie
import com.budianto.moviejetpackpro.core.domain.repository.IMovieRepository
import com.budianto.moviejetpackpro.core.util.AppExecutors
import com.budianto.moviejetpackpro.core.util.DataMapperMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {


    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
            object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
                override fun loadFromDB(): Flow<List<Movie>> {
                    return localDataSource.getAllMovies().map {
                        DataMapperMovie.mapEntitiesToDomain(it)
                    }
                }

                override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

                override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                        remoteDataSource.getAllMoviesFromApi()

                override suspend fun saveCallResult(data: List<MovieResponse>) {
                    val movieList = DataMapperMovie.mapResponseToEntities(data)
                    localDataSource.insertMovie(movieList)
                }
            }.asFlow()

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
            object : NetworkBoundResource<Movie, MovieResponse>(){
                override fun loadFromDB(): Flow<Movie> {
                    return localDataSource.getDetailMovie(movieId).map {
                        DataMapperMovie.mapEntityToDomain(it)
                    }
                }
                override fun shouldFetch(data: Movie?): Boolean = data === null

                override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                        remoteDataSource.getDetailMovie(movieId)

                override suspend fun saveCallResult(data: MovieResponse) {
                    val movieDetail = DataMapperMovie.mapResponseDetailToEntities(data)
                    localDataSource.updateMovie(movieDetail)
                }
            }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapperMovie.mapEntitiesToDomain(it)
        }
    }


    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapperMovie.mapDomainToEntities(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

}