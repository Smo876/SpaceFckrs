package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import de.mlex.spacefckrs.R
import de.mlex.spacefckrs.SpaceViewModel

@Composable
fun DrawAnimation(viewModel: SpaceViewModel, figureHeight: Dp) {
    val aniExplosion by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.explo))
    val aniExpProgress by animateLottieCompositionAsState(
        composition = aniExplosion,
        isPlaying = true,
        iterations = 1,
    )

    LaunchedEffect(Unit) {
        viewModel.playBrrrSound()
    }

    LottieAnimation(
        modifier = Modifier
            .size(figureHeight)
            .padding(top = 12.dp),
        composition = aniExplosion,
        progress = { aniExpProgress },
    )
}
