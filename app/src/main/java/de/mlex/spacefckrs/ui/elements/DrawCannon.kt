package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.R

@Composable
fun DrawCannon(
    isRunning: Boolean,
    cannon: Int,
    onShoot: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        enabled = isRunning,
        onClick = { onShoot(cannon) }) {
        Image(
            contentDescription = "Cannon",
            painter = painterResource(R.drawable.sf_cannon),
            contentScale = ContentScale.Fit,
            alpha = 3.0f,
            modifier = Modifier
                .size(180.dp)
        )
    }
}
