package com.budianto.moviejetpackpro.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    var movieId: Int,
    var title: String,
    var overview: String,
    var poster_path: String,
    var release_date: String,
    var vote_average: Double,
    var vote_count: Int,
    var isMovieFavorite: Boolean
) : Parcelable