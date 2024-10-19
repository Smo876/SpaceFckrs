package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import de.mlex.spacefckrs.SpaceViewModel

@Composable
fun GameScreen(viewModel: SpaceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AttackerScreen(viewModel.aliens.collectAsState())
        DefenseScreen { viewModel.executeMove(it) }
    }
}