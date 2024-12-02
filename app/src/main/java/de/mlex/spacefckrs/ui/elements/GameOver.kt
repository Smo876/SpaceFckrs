package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import de.mlex.spacefckrs.ui.theme.errorContainerDark

@Composable
fun GameOverBox(score: Int, newHighscore: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .fillMaxSize()
                .background(color = Color.DarkGray.copy(alpha = .8f))
        )
        val gradientColors1 = listOf(errorContainerDark, Color.Yellow)
        val gradientColors2 = listOf(Color.Green, Color.Blue)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GAME OVER",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors1
                    )
                )
            )
            Text(
                text = "Score: $score",
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Center,

                )
            if (newHighscore) {
                Text(
                    text = "NEW HIGHSCORE!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = gradientColors2
                        )
                    )
                )
            }
        }
    }
}