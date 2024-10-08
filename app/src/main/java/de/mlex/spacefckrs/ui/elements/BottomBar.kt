package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Filled.Build,
                contentDescription = "tech",
                modifier = Modifier.size(30.dp),
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Filled.Check,
                contentDescription = "go",
                modifier = Modifier.size(30.dp),
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "menu",
                modifier = Modifier.size(30.dp),
            )
        }

    }
}
