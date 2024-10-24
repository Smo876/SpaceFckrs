package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import de.mlex.spacefckrs.GameState
import de.mlex.spacefckrs.R
import de.mlex.spacefckrs.SpaceViewModel
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustScrap
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO

@Composable
fun GameScreen(viewModel: SpaceViewModel, padding: PaddingValues) {
    val aniExplosion by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.explosion))
    val aniExpProgress by animateLottieCompositionAsState(
        composition = aniExplosion,
        isPlaying = viewModel.aniExpIsPlaying.collectAsState().value
    )

    LaunchedEffect(key1 = aniExpProgress) {
        if (aniExpProgress == 1f) viewModel.cleanUp()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AttackerScreen(viewModel.aliens.collectAsState(), aniExplosion, aniExpProgress)
        DefenseScreen(viewModel.gameState.collectAsState().value) { viewModel.determineDamageAndExplode(it) }
        //if (gameState == GameState.GameIsRunning) DefenseScreen(true) { viewModel.determineDamageAndExplode(it) }
    }
}

@Composable
fun AttackerScreen(
    aliens: State<List<USO>>,
    aniExplosion: LottieComposition?,
    aniExpProgress: Float,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(aliens.value) {
            when (it) {
                is Alien -> DrawAlien(it, modifier = Modifier.size(90.dp))
                is JustSpace -> Spacer(Modifier.size(90.dp))
                is JustScrap -> {
                    if (aniExpProgress != 1f) {
                        LottieAnimation(modifier = Modifier.padding(top=16.dp),composition = aniExplosion, progress = { aniExpProgress })
                    }
                }
            }
        }
    }
}

@Composable
fun DefenseScreen(gameState: GameState, onShoot: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            DrawCannon((gameState == GameState.GameIsRunning), i, onShoot, modifier = Modifier.weight(1f))
        }
    }
}