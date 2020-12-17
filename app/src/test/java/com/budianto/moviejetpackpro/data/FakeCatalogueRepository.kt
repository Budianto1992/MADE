package com.budianto.moviejetpackpro.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.budianto.moviejetpackpro.data.source.local.LocalDataSource
import com.budianto.moviejetpackpro.data.source.local.entity.*
import com.budianto.moviejetpackpro.data.source.remote.ApiResponse
import com.budianto.moviejetpackpro.data.source.remote.RemoteDataSource
import com.budianto.moviejetpackpro.data.source.remote.remotemovie.CastMovieResponse
import com.budianto.moviejetpackpro.data.source.remote.remotemovie.MovieResponse
import com.budianto.moviejetpackpro.data.source.remote.remotetvshow.CastTvShowResponse
import com.budianto.moviejetpackpro.data.source.remote.remotetvshow.TvShowResponse
import com.budianto.moviejetpackpro.core.util.AppExecutors
import com.budianto.moviejetpackpro.core.data.Resource

class FakeCatalogueRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : _root_ide_package_.com.budianto.moviejetpackpro.core.data.CatalogueDataSource {


    //MovieRepository
    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(9)
                        .setPageSize(9)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                    remoteDataSource.getAllMovies()

            override fun saveCallResult(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(response.movieId,
                            response.title,
                            response.overview,
                            response.poster_path,
                            response.release_date,
                            false)
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieWithCasts(movieId: Int): LiveData<Resource<MovieWithCast>> {
        return object : NetworkBoundResource<MovieWithCast, List<CastMovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieWithCast> =
                    localDataSource.getMovieWithCastById(movieId)

            override fun shouldFetch(movieWithCast: MovieWithCast?): Boolean =
                    movieWithCast?.castMovies == null || movieWithCast.castMovies.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<CastMovieResponse>>> =
                    remoteDataSource.getAllCastMovies(movieId)

            override fun saveCallResult(castResponses: List<CastMovieResponse>) {
                val castList = ArrayList<CastMovieEntity>()
                for (response in castResponses) {
                    val cast = CastMovieEntity(response.castMovieId,
                            response.movieId,
                            response.name,
                            response.imgPoster)
                    castList.add(cast)
                }
                localDataSource.insertCastMovie(castList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(9)
                .setPageSize(9)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
            appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }

    //TvShow Repository

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val list = ArrayList<TvShowEntity>()
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(9)
                        .setPageSize(9)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                    remoteDataSource.getAllTvShows()

            override fun saveCallResult(tvShowResponses: List<TvShowResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in tvShowResponses) {
                    val tvShow = TvShowEntity(response.tvshowId,
                            response.title,
                            response.overview,
                            response.poster_path,
                            response.release_date,
                            false)
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowWithCasts(tvshowId: Int): LiveData<Resource<TvShowWithCast>> {
        return object : NetworkBoundResource<TvShowWithCast, List<CastTvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowWithCast> =
                    localDataSource.getTvShowWithCastById(tvshowId)

            override fun shouldFetch(tvshowWithCast: TvShowWithCast?): Boolean =
                    tvshowWithCast?.castTvShows == null || tvshowWithCast.castTvShows.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<CastTvShowResponse>>> =
                    remoteDataSource.getAllCastTvShows(tvshowId)

            override fun saveCallResult(castTvResponses: List<CastTvShowResponse>) {
                val castTvList = ArrayList<CastTvEntity>()
                for (response in castTvResponses) {
                    val castTv = CastTvEntity(response.castTvId,
                            response.tvshowId,
                            response.name,
                            response.imgPoster)
                    castTvList.add(castTv)
                }
                localDataSource.insertCastTvShow(castTvList)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val list = ArrayList<TvShowEntity>()
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(9)
                .setPageSize(9)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun setFavoriteTvshow(tvshow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvshow, state) }
    }
}