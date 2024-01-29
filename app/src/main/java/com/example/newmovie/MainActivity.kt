package com.example.newmovie

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newmovie.api.getMoviesFromApi
import com.example.newmovie.data.ApiResponse
import com.example.newmovie.data.Movie
import com.example.newmovie.ui.theme.MoviesNavigationGraph
import com.example.newmovie.ui.theme.NewMovieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewMovieTheme {
                MoviesNavigationGraph()

            }
        }
    }
}


