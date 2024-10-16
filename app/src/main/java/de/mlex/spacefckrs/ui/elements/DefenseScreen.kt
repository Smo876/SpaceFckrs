package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.R

@Composable
fun DefenseScreen(onShoot: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp, start = 14.dp, end = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
            DrawCannon(onShoot,
                modifier = Modifier
                    .weight(1f)
            )
            DrawCannon(onShoot,
                modifier = Modifier
                    .weight(1f)
            )
            DrawCannon(onShoot,
                modifier = Modifier
                    .weight(1f)
            )
            DrawCannon(onShoot,
                modifier = Modifier
                    .weight(1f)
            )
            DrawCannon(onShoot,
                modifier = Modifier
                    .weight(1f)
            )
        }

}

@Composable
fun DrawCannon(onShoot: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = { onShoot() }) {
        Image(
            modifier = modifier.padding(15.dp),
            contentDescription = "Cannon",
            painter = painterResource(R.drawable.sf_cannon)
        )
    }
}
