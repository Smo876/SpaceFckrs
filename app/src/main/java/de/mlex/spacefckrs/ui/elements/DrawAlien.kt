package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import de.mlex.spacefckrs.R

@Composable
fun DrawAlien(type: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        contentDescription = "Alien",
        painter = painterResource(type)
    )
}
