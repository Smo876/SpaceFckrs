package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.data.Alien

@Composable
fun DrawAlien(alien: Alien, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .wrapContentSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = modifier.padding(6.dp),
            contentDescription = "Alien",
            painter = painterResource(alien.type)
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp, end = 8.dp),
            text = "${alien.life}",
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black, Offset(-1.0f, -1.0f), blurRadius = 4f
                )
            )
        )
    }
}
