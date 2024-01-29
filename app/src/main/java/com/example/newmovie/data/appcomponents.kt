package com.example.newmovie.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.newmovie.ui.theme.MovieScreen
import com.example.newmovie.ui.theme.Routes
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun MovieCard(textvalue: String,
              year:String,
              imageUrl: String,
              modifier: Modifier,
              movie: Movie,
              navController: NavController
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageUrl) {
        val bitmap = withContext(Dispatchers.IO) {
            try {
                Picasso.get().load(imageUrl).get()
            } catch (e: Exception) {
                null
            }
        }
        imageBitmap = bitmap?.asImageBitmap()
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(7.dp)
            .width(150.dp)
            .height(200.dp)
            .clickable {
                navController.navigate("MOVIE_SCREEN/${movie.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(13.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = textvalue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
            )
            Spacer(modifier = Modifier.size(15.dp))
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                )

            }
            Spacer(modifier = Modifier.size(15.dp))
            Text(text =year)
        }



    }
}

@Composable
fun MovieList(movieList: List<Movie>,navController: NavController) {
    LazyColumn {
        items(
            items = movieList.chunked(2),
            itemContent = { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (movie in rowItems) {
                        MovieCard(
                            navController=navController,
                            textvalue = movie.title,
                            year = movie.release_date.take(4)+".",
                            imageUrl = movie.backdrop_path,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                           navController.navigate(Routes.MOVIE_SCREEN)
                                },
                            movie = movie


                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        )
    }
}
@Composable
fun MyTextField(value: String, onValueChange: (String) -> Unit,modifier: Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Enter film name", color = Color.DarkGray) },
        modifier = Modifier
            .fillMaxWidth()

    )
}