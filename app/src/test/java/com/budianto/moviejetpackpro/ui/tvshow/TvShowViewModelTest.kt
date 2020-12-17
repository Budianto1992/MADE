package com.budianto.moviejetpackpro.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.budianto.moviejetpackpro.data.CatalogueRepository
import com.budianto.moviejetpackpro.data.source.local.entity.TvShowEntity
import com.budianto.moviejetpackpro.core.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(catalogueRepository)
    }

    @Test
    fun getAllTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(10)
        val tvshows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvshows.value = dummyTvShows

        `when`(catalogueRepository.getAllTvShows()).thenReturn(tvshows)
        val tvshowEntities = viewModel.getAllTvShows().value?.data
        verify(catalogueRepository).getAllTvShows()
        assertNotNull(tvshowEntities)
        assertEquals(10, tvshowEntities?.size)

        viewModel.getAllTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}