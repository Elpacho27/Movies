package com.example.newmovie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newmovie.api.getMoviesFromApi
import com.example.newmovie.api.getRequest
import com.example.newmovie.data.Movie
import com.example.newmovie.data.MovieList
import com.example.newmovie.data.MyTextField

@Composable
fun FirstScreen(navController: NavHostController) {

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
            }
        }
        var query by remember { mutableStateOf("") }
        val movielist= remember { mutableStateListOf<Movie>() }

        MyTextField(value = query, onValueChange = { newValue ->
            query = newValue
        }, modifier =Modifier)

        LaunchedEffect(query) {
            if (query.isNotEmpty()) {
                val req = getRequest(query)
                getMoviesFromApi(req) { apiResponse ->
                    if (apiResponse != null) {
                        movielist.clear()
                        movielist.addAll(apiResponse.results)
                    } else {
                        println("Nije moguće dobiti odgovor")
                    }
                } }
        }
        MovieList(movielist,navController)
    }

}