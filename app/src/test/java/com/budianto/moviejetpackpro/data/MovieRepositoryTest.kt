package com.budianto.moviejetpackpro.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.budianto.moviejetpackpro.data.source.local.LocalDataSource
import com.budianto.moviejetpackpro.data.source.local.entity.MovieEntity
import com.budianto.moviejetpackpro.data.source.local.entity.MovieWithCast
import com.budianto.moviejetpackpro.data.source.local.entity.TvShowEntity
import com.budianto.moviejetpackpro.data.source.local.entity.TvShowWithCast
import com.budianto.moviejetpackpro.data.source.remote.RemoteDataSource
import com.budianto.moviejetpackpro.core.util.AppExecutors
import com.budianto.moviejetpackpro.core.data.Resource
import com.dicoding.academies.utils.LiveDataTestUtil
import com.dicoding.academies.utils.PagedListUtil
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val catalogueRepository = FakeCatalogueRepository(remoteDataSource, localDataSource, appExecutors)

    private val movieResponses = DummyMovie.generateRemoteMovies()
    private val movieId = movieResponses[0].movieId
    private val castMovieResponses = DummyMovie.generateDummyCastMovie(movieId)

    private val tvshowResponses = DummyMovie.generateRemoteTvShows()
    private val tvshowId = tvshowResponses[0].tvshowId
    private val castTvShowResponses = DummyMovie.generateDummyCastTvShow(tvshowId)

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getAllMovies()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyMovie.generateMovies()))
        verify(localDataSource).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieWithCasts() {
        val dummyEntity = MutableLiveData<MovieWithCast>()
        dummyEntity.value = DummyMovie.generateDummyMovieWithCast(DummyMovie.generateMovies()[0], false)
        `when`(localDataSource.getMovieWithCastById(movieId)).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(catalogueRepository.getMovieWithCasts(movieId))
        verify(localDataSource).getMovieWithCastById(movieId)
        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.mMovie?.title)
        assertEquals(movieResponses[0].title, movieEntities.data?.mMovie?.title)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteMovie()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyMovie.generateMovies()))
        verify(localDataSource).getFavoriteMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getAllTvShows()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllTvShows()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DummyMovie.generateTvShows()))
        verify(localDataSource).getAllTvShows()
        assertNotNull(tvshowEntities.data)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowWithCasts() {
        val dummyEntity = MutableLiveData<TvShowWithCast>()
        dummyEntity.value = DummyMovie.generateDummyTvShowWithCast(DummyMovie.generateTvShows()[0], false)
        `when`(localDataSource.getTvShowWithCastById(tvshowId)).thenReturn(dummyEntity)

        val tvshowEntities = LiveDataTestUtil.getValue(catalogueRepository.getTvShowWithCasts(tvshowId))
        verify(localDataSource).getTvShowWithCastById(tvshowId)
        assertNotNull(tvshowEntities.data)
        assertNotNull(tvshowEntities.data?.mTvShow?.title)
        assertEquals(tvshowResponses[0].title, tvshowEntities.data?.mTvShow?.title)
    }

    @Test
    fun getFavoriteTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteTvShow()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DummyMovie.generateTvShows()))
        verify(localDataSource).getFavoriteTvShow()
        assertNotNull(tvshowEntities.data)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }
}