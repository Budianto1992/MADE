package com.budianto.moviejetpackpro.ui.detail.detailtvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.budianto.moviejetpackpro.data.CatalogueRepository
import com.budianto.moviejetpackpro.data.source.local.entity.TvShowWithCast
import com.budianto.moviejetpackpro.core.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dummyTvShow = DummyMovie.generateTvShows()[0]
    private val tvshowId = dummyTvShow.tvshowId
    private val dummyTvCast = DummyMovie.generateDummyCastTvShow(tvshowId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvShowWithCast>>

    @Before
    fun setUp(){
        viewModel = DetailTvShowViewModel(catalogueRepository)
        viewModel.setSelectedTvShow(tvshowId)
    }

    @Test
    fun getTvshowCast() {
        val dummyTvShowWithCast = Resource.success(DummyMovie.generateDummyTvShowWithCast(dummyTvShow, true))
        val tvshow = MutableLiveData<Resource<TvShowWithCast>>()
        tvshow.value = dummyTvShowWithCast

        `when`(catalogueRepository.getTvShowWithCasts(tvshowId)).thenReturn(tvshow)
        viewModel.tvshowCast.observeForever(observer)
        verify(observer).onChanged(dummyTvShowWithCast)
    }

}