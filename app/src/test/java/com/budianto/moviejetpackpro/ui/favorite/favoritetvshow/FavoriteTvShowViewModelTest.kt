package com.budianto.moviejetpackpro.ui.favorite.favoritetvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.budianto.moviejetpackpro.data.CatalogueRepository
import com.budianto.moviejetpackpro.data.source.local.entity.TvShowEntity
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(catalogueRepository)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShow = pagedList
        `when`(dummyTvShow.size).thenReturn(10)
        val tvshows = MutableLiveData<PagedList<TvShowEntity>>()
        tvshows.value = dummyTvShow

        `when`(catalogueRepository.getFavoriteTvShow()).thenReturn(tvshows)
        val tvshowEntities = viewModel.getFavoriteTvShows().value
        verify(catalogueRepository).getFavoriteTvShow()
        assertNotNull(tvshowEntities)
        assertEquals(10, tvshowEntities?.size)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}