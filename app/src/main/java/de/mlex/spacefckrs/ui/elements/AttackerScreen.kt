package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO


@Composable
fun AttackerScreen(aliens: State<List<USO>>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(top = 40.dp, bottom = 10.dp, start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(aliens.value) {
            when (it) {
                is Alien -> DrawAlien(it, modifier = Modifier.size(90.dp))
                is JustSpace -> Spacer(Modifier.size(90.dp))
            }
        }
    }
}