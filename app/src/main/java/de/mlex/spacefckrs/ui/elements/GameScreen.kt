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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import de.mlex.spacefckrs.CannonState
import de.mlex.spacefckrs.R
import de.mlex.spacefckrs.SpaceViewModel
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustScrap
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    viewModel: SpaceViewModel,
    padding: PaddingValues,
) {
    val aniExplosion by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.explosion))
    val aniExpProgress by animateLottieCompositionAsState(
        composition = aniExplosion,
        isPlaying = viewModel.aniExpIsPlaying.collectAsState().value
    )

    LaunchedEffect(key1 = aniExpProgress) {
        if (aniExpProgress == 1f) {
            viewModel.cleanUp()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.Black),
        contentAlignment = Alignment.BottomStart
    ) {
        DefenseScreen(
            viewModel.cannonState.value,
            Modifier
                .padding(bottom = 18.dp)
                .height(50.dp)
        ) { viewModel.determineDamageAndExplode(it) }

        AttackerScreen(
            viewModel.aliens.collectAsState(),
            aniExplosion,
            aniExpProgress,
            Modifier
                .padding(bottom = 18.dp)
                .fillMaxSize()
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AttackerScreen(
    aliens: State<List<USO>>,
    aniExplosion: LottieComposition?,
    aniExpProgress: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxHeight(),
    ) {
        val figureHeight = maxHeight / 7
        LazyVerticalGrid(

            columns = GridCells.Fixed(5),
        ) {
            items(aliens.value) {
                when (it) {
                    is Alien -> DrawAlien(it, modifier = Modifier.size(figureHeight))
                    is JustSpace -> Spacer(Modifier.size(figureHeight))
                    is JustScrap -> {
                        if (aniExpProgress != 1f) {
                            LottieAnimation(modifier = Modifier
                                .size(figureHeight)
                                .padding(top = 12.dp),
                                composition = aniExplosion,
                                progress = { aniExpProgress })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DefenseScreen(
    cannonState: CannonState,
    modifier: Modifier,
    onShoot: (Int) -> Unit
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            DrawCannon(
                cannonState, i, onShoot, modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
            )
        }
    }
}