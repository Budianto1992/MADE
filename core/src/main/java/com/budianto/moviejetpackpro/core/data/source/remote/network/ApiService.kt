package com.budianto.moviejetpackpro.core.data.source.remote.network

import com.budianto.moviejetpackpro.core.data.source.remote.remotemovie.ListMovieResponse
import com.budianto.moviejetpackpro.core.data.source.remote.remotemovie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getListMovieFromApi(@Query("api_key") apiKey: String): ListMovieResponse

    @GET("movie/{movieid}")
    suspend fun getDetailMovieFromApi(@Path("movieId") movieId: Int,
                                      @Query("api_key") apiKey: String): MovieResponse
}