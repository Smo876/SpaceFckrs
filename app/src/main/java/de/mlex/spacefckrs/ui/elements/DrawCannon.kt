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
    modifier: Modifier = Modifier,
    painterResource: Int = when (cannonState) {
        CannonState.IsReady -> R.drawable.sf_cannon
        CannonState.WasDestroyed -> R.drawable.sf_destroyed_cannon
        else -> {
            if ((cannonState == CannonState.A_IsFiring && cannon == 1)
                || (cannonState == CannonState.B_IsFiring && cannon == 2)
                || (cannonState == CannonState.C_IsFiring && cannon == 3)
                || (cannonState == CannonState.D_IsFiring && cannon == 4)
                || (cannonState == CannonState.E_IsFiring && cannon == 5)
            )
                R.drawable.sf_firing_cannon
            else R.drawable.sf_cannon
        }
    }
) {
    IconButton(
        modifier = modifier,
        enabled = (cannonState == CannonState.IsReady),
        onClick = { onShoot(cannon) }) {
        Image(
            contentDescription = "Cannon",
            painter = painterResource(painterResource),
            contentScale = ContentScale.Fit,
            alpha = 3.0f,
            modifier = Modifier
                .size(180.dp)
        )
    }
}
