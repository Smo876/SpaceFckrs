package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.Alien


@Composable
fun PlayFieldGrid(aliens: List<Alien>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 10.dp, start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(aliens.size) { it ->
            DrawAlien(
                type = aliens[it].type,
                modifier = Modifier
                    .size(90.dp)
                    .padding(1.dp)
            )
        }
    }
}

