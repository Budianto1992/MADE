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

//    private val handler = Handler()
//    companion object{
//        private const val SERVICE_LATENCY_IN_MILIS: Long = 2000
//
//        @Volatile
//        private var instance: RemoteDataSource? = null
//
//        fun getInstance(helper: JsonHelper): RemoteDataSource =
//            instance ?: synchronized(this){
//                instance ?: RemoteDataSource(helper)
//            }
//    }

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



//    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
//        EspressoIdlingResource.increment()
//        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
//        handler.postDelayed({
//            resultMovies.value = ApiResponse.success(jsonHelper.loadMovies())
//            EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILIS)
//        return resultMovies
//    }
//
//    fun getAllCastMovies(movieId: Int): LiveData<ApiResponse<List<CastMovieResponse>>> {
//        EspressoIdlingResource.increment()
//        val resultCastMovies = MutableLiveData<ApiResponse<List<CastMovieResponse>>>()
//        handler.postDelayed({
//            resultCastMovies.value = ApiResponse.success(jsonHelper.loadCastMovies(movieId))
//            EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILIS)
//        return resultCastMovies
//    }
//
//    fun getAllTvShows(): LiveData<ApiResponse<List<TvShowResponse>>> {
//        EspressoIdlingResource.increment()
//        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
//        handler.postDelayed({
//            resultTvShows.value = ApiResponse.success(jsonHelper.loadTvShows())
//            EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILIS)
//        return resultTvShows
//    }
//
//    fun getAllCastTvShows(tvshowId: Int): LiveData<ApiResponse<List<CastTvShowResponse>>> {
//        EspressoIdlingResource.increment()
//        val resultCastTvShows = MutableLiveData<ApiResponse<List<CastTvShowResponse>>>()
//        handler.postDelayed({
//            resultCastTvShows.value = ApiResponse.success(jsonHelper.loadCastTvShow(tvshowId))
//            EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILIS)
//        return resultCastTvShows
//    }
}