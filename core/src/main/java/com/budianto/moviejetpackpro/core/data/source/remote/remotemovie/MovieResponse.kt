package com.budianto.moviejetpackpro.core.data.source.remote.remotemovie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResponse (

    @field:SerializedName("id")
    var movieId: Int,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("poster_path")
    var poster_path: String,

    @field:SerializedName("release_date")
    var release_date: String,

    @field:SerializedName("vote_average")
    var vote_average: Double,

    @field:SerializedName("vote_count")
    var vote_count: Int
) : Parcelable