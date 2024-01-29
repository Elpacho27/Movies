package com.example.newmovie.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newmovie.FirstScreen
import com.example.newmovie.data.Movie

@Composable
fun MoviesNavigationGraph( ){

    val navController= rememberNavController()


    NavHost(navController =navController , startDestination = Routes.FIRST_SCREEN){


        composable(Routes.FIRST_SCREEN){
            FirstScreen(navController=navController)
        }
        composable(Routes.QUERY_SCREEN){
            QueryScreen()
        }
        composable(Routes.MOVIE_SCREEN){
            MovieScreen()
        }

    }
}