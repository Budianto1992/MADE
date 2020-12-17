package com.budianto.moviejetpackpro.core.util

import com.budianto.moviejetpackpro.core.data.source.local.entity.MovieEntity
import com.budianto.moviejetpackpro.core.data.source.remote.remotemovie.MovieResponse
import com.budianto.moviejetpackpro.core.domain.model.Movie

object DataMapperMovie {

    fun mapResponseToEntities(input: List<MovieResponse>): List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                    movieId = it.movieId,
                    title = it.title,
                    overview = it.overview,
                    poster_path = it.poster_path,
                    release_date = it.release_date,
                    vote_average = it.vote_average,
                    vote_count = it.vote_count,
                    isMovieFavorite = false
            )

            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseDetailToEntities(input: MovieResponse): MovieEntity{
        return MovieEntity(
                movieId = input.movieId,
                title = input.title,
                overview = input.overview,
                poster_path = input.poster_path,
                release_date = input.release_date,
                vote_count = input.vote_count,
                vote_average = input.vote_average
        )
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                    movieId = it.movieId,
                    title = it.title,
                    overview = it.overview,
                    poster_path = it.poster_path,
                    release_date = it.release_date,
                    vote_average = it.vote_average,
                    vote_count = it.vote_count,
                    isMovieFavorite = it.isMovieFavorite
            )
        }

    fun mapDomainToEntities(input: Movie) = MovieEntity(
            movieId = input.movieId,
            title = input.title,
            overview = input.overview,
            poster_path = input.poster_path,
            release_date = input.release_date,
            vote_average = input.vote_average,
            vote_count = input.vote_count,
            isMovieFavorite = input.isMovieFavorite
    )

    fun mapEntityToDomain(input: MovieEntity) = Movie(
            movieId = input.movieId,
            title = input.title,
            overview = input.overview,
            poster_path = input.poster_path,
            release_date = input.release_date,
            vote_average = input.vote_average,
            vote_count = input.vote_count,
            isMovieFavorite = input.isMovieFavorite
    )
}