package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import de.mlex.spacefckrs.CannonState
import de.mlex.spacefckrs.R

@Composable
fun DrawCannon(
    cannonState: CannonState,
    cannon: Int,
    onShoot: (Int) -> Unit,
    modifier: Modifier = Modifier,
    painterResource: Int = when (cannonState) {
        CannonState.IsReady -> R.drawable.sf_cannon
        CannonState.WasDestroyed -> R.drawable.sf_destroyedcannon
        else -> {
            if ((cannonState == CannonState.AIsFiring && cannon == 1)
                || (cannonState == CannonState.BIsFiring && cannon == 2)
                || (cannonState == CannonState.CIsFiring && cannon == 3)
                || (cannonState == CannonState.DIsFiring && cannon == 4)
                || (cannonState == CannonState.EIsFiring && cannon == 5)
            )
                R.drawable.sf_firingcannon
            else R.drawable.sf_cannon
        }
    }
) {
    IconButton(
        modifier = modifier,
        enabled = (cannonState == CannonState.IsReady),
        onClick = {
            onShoot(cannon)
        }) {
        Image(
            contentDescription = "Cannon",
            painter = painterResource(painterResource),
            contentScale = ContentScale.Fit,
            alpha = 1.0f,
        )
    }
}
