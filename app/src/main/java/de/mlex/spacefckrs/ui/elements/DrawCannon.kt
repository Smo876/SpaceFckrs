package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import de.mlex.spacefckrs.CannonState
import de.mlex.spacefckrs.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun DrawCannon(
    cannonState: CannonState,
    cannon: Int,
    onShoot: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShooting by remember { mutableStateOf(false)}
    val coroutine = rememberCoroutineScope()
    IconButton(
        modifier = modifier,
        enabled = (cannonState == CannonState.IsReady),
        onClick = {
            onShoot(cannon)
            isShooting = true
            coroutine.launch {
                delay(0.5.seconds)
                isShooting = false
            }
        }) {
        Image(
            contentDescription = "Cannon",
            painter = painterResource(
                when {
                    cannonState == CannonState.WasDestroyed ->  R.drawable.sf_destroyedcannon
                    isShooting -> R.drawable.sf_firingcannon
                    else -> R.drawable.sf_cannon
                }
            ),
            contentScale = ContentScale.Fit,
            alpha = 1.0f,
        )
    }
}
