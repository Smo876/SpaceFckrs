package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.mlex.spacefckrs.ui.theme.backgroundDark
import de.mlex.spacefckrs.ui.theme.onPrimaryContainerDark

@Composable
fun TopBar(score: Int, hightscore: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .topBarBorder(3.dp, color = backgroundDark),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            val gradientColors = listOf(Color.Blue, Color.Red)
            Text(
                text = "SPACE FCKRS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append("SCORE: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = onPrimaryContainerDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append("$score")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append(" (")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = onPrimaryContainerDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append("$hightscore")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append(")")
                    }
                })
        }
    }
}

fun Modifier.topBarBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)