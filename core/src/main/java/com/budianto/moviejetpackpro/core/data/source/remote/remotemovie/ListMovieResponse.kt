package com.budianto.moviejetpackpro.core.data.source.remote.remotemovie

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

    @field:SerializedName("page")
    val page: String,

    @field:SerializedName("total_results")
    val total_results: String,


    @field:SerializedName("results")
    val results: List<MovieResponse>
)