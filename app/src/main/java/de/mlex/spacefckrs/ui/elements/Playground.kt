package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Playground() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp, bottom = 52.dp)
            .background(Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Alien(
            color = Color.Red,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Gray,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Green,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Blue,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Green,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )

    }
}

@Composable
fun PlayFieldGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(20) {
            Alien(
                color = Color.Green,
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
            )
        }
    }
}

