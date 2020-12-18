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
                override fun shouldFetch(data: Movie?): Boolean =
                    data === null || data.overview === null || data.title === null

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

//    override fun cleareFavoriteMovie() {
//        GlobalScope.launch {
//            localDataSource.cleareFavoriteMovie()
//        }
//    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapperMovie.mapDomainToEntities(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

//    //MovieRepository
//    override fun getAllMovies(): Flow<Resource<Resource<Movie>>> {
//        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
//            override fun loadFromDB(): Flow<PagedList<Movie>> {
//                return localDataSource.getAllMovies().map {
//                    DataMapperMovie.mapMovieEntitiesToDomain(it)
//                }
//            }
//            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
//                data == null || data.isEmpty()
//            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
//                remoteDataSource.getAllMovies()
//            override suspend fun saveCallResult(movieResponses: List<MovieResponse>) {
//                val movieList = ArrayList<MovieEntity>()
//                for (response in movieResponses){
//                    val movie = MovieEntity(response.movieId,
//                        response.title,
//                        response.overview,
//                        response.poster_path,
//                        response.release_date,
//                        false)
//                    movieList.add(movie)
//                }
//                localDataSource.insertMovie(movieList)
//            }
//        }.asLiveData()
//    }
//
//    override fun getMovieWithCasts(movieId: Int): LiveData<Resource<MovieWithCast>> {
//        return object : NetworkBoundResource<MovieWithCast, List<CastMovieResponse>>(appExecutors){
//            override fun loadFromDB(): LiveData<MovieWithCast> =
//                localDataSource.getMovieWithCastById(movieId)
//
//            override fun shouldFetch(movieWithCast: MovieWithCast?): Boolean =
//                movieWithCast?.castMovies == null || movieWithCast.castMovies.isEmpty()
//
//            override fun createCall(): LiveData<ApiResponse<List<CastMovieResponse>>> =
//                remoteDataSource.getAllCastMovies(movieId)
//
//            override fun saveCallResult(castResponses: List<CastMovieResponse>) {
//                val castList = ArrayList<CastMovieEntity>()
//                for (response in castResponses){
//                    val cast = CastMovieEntity(response.castMovieId,
//                        response.movieId,
//                        response.name,
//                        response.imgPoster)
//                    castList.add(cast)
//                }
//                localDataSource.insertCastMovie(castList)
//            }
//        }.asLiveData()
//    }
//
//    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
//        val config = PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setInitialLoadSizeHint(9)
//                .setPageSize(9)
//                .build()
//        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
//    }
//
//    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
//        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
//
//    //TvShow Repository
//
//    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
//        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors){
//            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
//                val list = ArrayList<TvShowEntity>()
//                val config = PagedList.Config.Builder()
//                        .setEnablePlaceholders(false)
//                        .setInitialLoadSizeHint(9)
//                        .setPageSize(9)
//                        .build()
//                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
//            }
//
//            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
//                data == null || data.isEmpty()
//
//            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
//                remoteDataSource.getAllTvShows()
//
//            override fun saveCallResult(tvShowResponses: List<TvShowResponse>) {
//                val tvShowList = ArrayList<TvShowEntity>()
//                for (response in tvShowResponses){
//                    val tvShow = TvShowEntity(response.tvshowId,
//                        response.title,
//                        response.overview,
//                        response.poster_path,
//                        response.release_date,
//                        false)
//                    tvShowList.add(tvShow)
//                }
//                localDataSource.insertTvShow(tvShowList)
//            }
//        }.asLiveData()
//    }
//
//    override fun getTvShowWithCasts(tvshowId: Int): LiveData<Resource<TvShowWithCast>> {
//        return object : NetworkBoundResource<TvShowWithCast, List<CastTvShowResponse>>(appExecutors){
//            override fun loadFromDB(): LiveData<TvShowWithCast> =
//                localDataSource.getTvShowWithCastById(tvshowId)
//
//            override fun shouldFetch(tvshowWithCast: TvShowWithCast?): Boolean =
//                tvshowWithCast?.castTvShows == null || tvshowWithCast.castTvShows.isEmpty()
//
//            override fun createCall(): LiveData<ApiResponse<List<CastTvShowResponse>>> =
//                remoteDataSource.getAllCastTvShows(tvshowId)
//
//            override fun saveCallResult(castTvResponses: List<CastTvShowResponse>) {
//                val castTvList = ArrayList<CastTvEntity>()
//                for (response in castTvResponses){
//                    val castTv = CastTvEntity(response.castTvId,
//                        response.tvshowId,
//                        response.name,
//                        response.imgPoster)
//                    castTvList.add(castTv)
//                }
//                localDataSource.insertCastTvShow(castTvList)
//            }
//        }.asLiveData()
//    }
//
//    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
//        val config = PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setInitialLoadSizeHint(9)
//                .setPageSize(9)
//                .build()
//        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
//    }
//
//    override fun setFavoriteTvshow(tvshow: TvShowEntity, state: Boolean) {
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvshow, state) }
//    }
}