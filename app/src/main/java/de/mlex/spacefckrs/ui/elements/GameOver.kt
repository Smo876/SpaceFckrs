package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import de.mlex.spacefckrs.SpaceViewModel
import de.mlex.spacefckrs.ui.theme.errorContainerDark

@Composable
fun GameOverBox(viewModel: SpaceViewModel, padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        GameScreen(viewModel, padding)
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .fillMaxSize()
                .background(color = Color.DarkGray.copy(alpha = .8f))
        )
        val gradientColors = listOf(errorContainerDark, Color.Yellow)
        Text(
            text = "GAME OVER",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
        )
    }
}