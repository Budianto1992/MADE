package com.budianto.moviejetpackpro.core.data.source.remote

import android.util.Log
import com.budianto.moviejetpackpro.core.data.source.remote.network.ApiResponse
import com.budianto.moviejetpackpro.core.data.source.remote.network.ApiService
import com.budianto.moviejetpackpro.core.data.source.remote.remotemovie.MovieResponse
import com.budianto.moviejetpackpro.core.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource constructor(private val apiService: ApiService){

    suspend fun getAllMoviesFromApi(): Flow<ApiResponse<List<MovieResponse>>>{
        return flow {
            try {
                val response = apiService.getListMovieFromApi(Constant.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else{
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(movieId: Int): Flow<ApiResponse<MovieResponse>>{
        return flow {
            try {
                val response = apiService.getDetailMovieFromApi(movieId, Constant.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}