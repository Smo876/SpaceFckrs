package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.SpaceViewModel
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO

@Composable
fun GameScreen(viewModel: SpaceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 37.dp, bottom = 50.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AttackerScreen(viewModel.aliens.collectAsState())
        DefenseScreen { viewModel.executeMove(it) }
    }
}

@Composable
fun AttackerScreen(aliens: State<List<USO>>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp),
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

@Composable
fun DefenseScreen(onShoot: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp, start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            DrawCannon(i, onShoot, modifier = Modifier.weight(1f))
        }
    }
}