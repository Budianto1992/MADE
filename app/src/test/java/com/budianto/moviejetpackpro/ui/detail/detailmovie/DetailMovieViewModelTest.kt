package com.budianto.moviejetpackpro.ui.detail.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.budianto.moviejetpackpro.data.CatalogueRepository
import com.budianto.moviejetpackpro.data.source.local.entity.MovieWithCast
import com.budianto.moviejetpackpro.core.data.Resource
import com.budianto.moviejetpackpro.ui.detail.DetailMovieViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DummyMovie.generateMovies()[0]
    private val movieId = dummyMovie.movieId
    private val dummyCastMovie = DummyMovie.generateDummyCastMovie(movieId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieWithCast>>

    @Before
    fun setUp(){
        viewModel = DetailMovieViewModel(catalogueRepository)
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun getMovieCast() {
        val dummyMovieWithCast = Resource.success(DummyMovie.generateDummyMovieWithCast(dummyMovie, true))
        val movie = MutableLiveData<Resource<MovieWithCast>>()
        movie.value = dummyMovieWithCast

        `when`(catalogueRepository.getMovieWithCasts(movieId)).thenReturn(movie)

        viewModel.movieCast.observeForever(observer)
        verify(observer).onChanged(dummyMovieWithCast)
    }

}