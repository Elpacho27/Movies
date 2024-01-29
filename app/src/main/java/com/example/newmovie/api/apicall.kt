package com.example.newmovie.api

import android.util.Log
import com.example.newmovie.data.ApiResponse
import com.example.newmovie.data.Movie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

fun parseJsonToMovieData(jsonString: String): ApiResponse? {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(ApiResponse::class.java, Movie::class.java)
    val adapter: JsonAdapter<ApiResponse> = moshi.adapter(type)

    return try {
        val apiResponse = adapter.fromJson(jsonString)

        val updatedMovies = apiResponse?.results?.map { movie ->
            val updatedBackdropPath = if (movie.backdrop_path == "https://image.tmdb.org/t/p/originalnull") {
                "https://media.istockphoto.com/id/911590226/vector/movie-time-vector-illustration-cinema-poster-concept-on-red-round-background-composition-with.jpg?s=612x612&w=0&k=20&c=QMpr4AHrBgHuOCnv2N6mPUQEOr5Mo8lE7TyWaZ4r9oo="
            } else {
                movie.backdrop_path ?: ""
            }

            val updatedTitle = if (movie.title.length >= 12) {
                movie.title.take(12)
            } else {
                movie.title
            }

            movie.copy(backdrop_path = updatedBackdropPath, title = updatedTitle)
        }

        val updatedResponse = apiResponse?.copy(results = updatedMovies.orEmpty())

        updatedResponse
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getRequest(query:String):Request{
    val request = Request.Builder()
        .url("https://advanced-movie-search.p.rapidapi.com/search/movie?query=${query}&page=1")
        .get()
        .addHeader("X-RapidAPI-Key", "4676d6db05msh5a86808a602fe1dp177e55jsn3c38c37d7b03")
        .addHeader("X-RapidAPI-Host", "advanced-movie-search.p.rapidapi.com")
        .build()

    return request

}



fun getMoviesFromApi(req:Request ,callback: (ApiResponse?) -> Unit){

    val client=OkHttpClient()
    val request = req

    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            println("Error: ${e.message}")
            callback(null)

        }
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body!!.string()
                val moviedata = parseJsonToMovieData(responseBody)

                callback(moviedata)
            } else {
                callback(null)
            }
        }


    })
}