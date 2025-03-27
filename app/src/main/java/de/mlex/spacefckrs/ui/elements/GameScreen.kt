package de.mlex.spacefckrs.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.SpaceViewModel
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustScrap
import de.mlex.spacefckrs.data.JustSpace

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    viewModel: SpaceViewModel,
    padding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.Black),
        contentAlignment = Alignment.BottomStart
    ) {
        DefenseScreen(
            viewModel,
            Modifier
                .padding(bottom = 18.dp)
                .height(50.dp)
        )

        AttackerScreen(
            viewModel,
            Modifier
                .padding(bottom = 18.dp)
                .fillMaxSize()
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AttackerScreen(
    viewModel: SpaceViewModel,
    modifier: Modifier = Modifier,
) {
    val aliens by viewModel.aliens.collectAsState()
    BoxWithConstraints(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxHeight(),
    ) {
        val figureHeight = maxHeight / 7
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
        ) {
            items(aliens, { alien -> alien.id } ){
                when (it) {
                    is JustSpace -> Spacer(Modifier.size(figureHeight))
                    is Alien -> DrawAlien(it, modifier = Modifier.size(figureHeight))
                    is JustScrap -> DrawAnimation(viewModel, figureHeight)
                }
            }
        }
    }
}

@Composable
fun DefenseScreen(
    viewModel: SpaceViewModel,
    modifier: Modifier
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            DrawCannon(
                viewModel, i, modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
            )
        }
    }
}