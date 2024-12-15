package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import de.mlex.spacefckrs.CannonState
import de.mlex.spacefckrs.R
import de.mlex.spacefckrs.SpaceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun DrawCannon(
    viewModel: SpaceViewModel,
    cannon: Int,
    onShoot: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isShooting = remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val cannonState by viewModel.cannonState.collectAsState()
    IconButton(
        modifier = modifier,
        enabled = (cannonState == CannonState.IsReady),
        onClick = {
            onShoot(cannon)
            coroutine.animateCannon(isShooting, viewModel)
        }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Cannon",
            painter = painterResource(
                when {
                    cannonState == CannonState.WasDestroyed -> R.drawable.sf_destroyedcannon
                    isShooting.value -> R.drawable.sf_firingcannon
                    else -> R.drawable.sf_cannon
                }
            ),
            contentScale = ContentScale.Fit,
            alpha = 1.0f,
        )
    }
}

private fun CoroutineScope.animateCannon(
    isShooting: MutableState<Boolean>,
    viewModel: SpaceViewModel
) {
    isShooting.value = true
    launch {
        viewModel.playPiuSound()
        delay(0.5.seconds)
        isShooting.value = false
    }
}
