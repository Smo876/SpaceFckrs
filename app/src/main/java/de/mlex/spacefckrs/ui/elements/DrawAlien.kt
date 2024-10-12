package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DrawAlien(type: Int, life: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .wrapContentSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = modifier.padding(6.dp),
            contentDescription = "Alien",
            painter = painterResource(type)
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = "$life",
            fontWeight = FontWeight.Bold,
        )
    }
}
