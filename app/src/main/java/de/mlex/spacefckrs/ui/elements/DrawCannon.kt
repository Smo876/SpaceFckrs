package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.CannonState
import de.mlex.spacefckrs.R

@Composable
fun DrawCannon(
    cannonState: CannonState,
    cannon: Int,
    onShoot: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        enabled = ( cannonState == CannonState.IsReady),
        onClick = { onShoot(cannon) }) {
        Image(
            contentDescription = "Cannon",
            painter = painterResource(
                id = when (cannonState) {
                    CannonState.WasDestroyed -> R.drawable.sf_destroyedcannon
                    CannonState.IsFiring -> R.drawable.sf_firingcannon
                    else -> R.drawable.sf_cannon
                }
            ),
            contentScale = ContentScale.Fit,
            alpha = 3.0f,
            modifier = Modifier
                .size(180.dp)
        )
    }
}
