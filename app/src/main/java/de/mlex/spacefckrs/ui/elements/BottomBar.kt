package de.mlex.spacefckrs.ui.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.mlex.spacefckrs.R
import de.mlex.spacefckrs.ui.theme.backgroundDark
import de.mlex.spacefckrs.ui.theme.onPrimaryContainerDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun BottomBar(
    nextDamage: Int,
    soundOn: Boolean,
    resetGame: () -> Unit,
    switchSoundSetting: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .bottomBarBorder(3.dp, color = backgroundDark),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append("FORCE: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = onPrimaryContainerDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append("$nextDamage")
                    }
                })

            Row() {
                if (soundOn) {
                    IconButton(onClick = { switchSoundSetting() }) {
                        Icon(
                            painterResource(R.drawable.baseline_music_note_24),
                            contentDescription = "sound",
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color.LightGray
                        )
                    }
                } else {
                    IconButton(onClick = { switchSoundSetting() }) {
                        Icon(
                            painterResource(R.drawable.outline_music_note_24),
                            contentDescription = "sound",
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color.LightGray
                        )
                    }
                }

                val degree = remember { Animatable(0f) }
                val scope1 = rememberCoroutineScope()
                val scope2 = rememberCoroutineScope()
                val scope3 = rememberCoroutineScope()
                IconButton(onClick = {
                    scope1.launch { degree.stop() }
                    scope2.launch {
                        if (degree.value != 0f) degree.snapTo(0f)
                        degree.animateTo(
                            360f,
                            SpringSpec(
                                dampingRatio = 0.75f,
                                stiffness = 100f,
                            )
                        )
                    }
                    scope3.launch {
                        delay(0.5.seconds)
                        resetGame()
                    }

                }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "refresh",
                        modifier = Modifier
                            .rotate(degree.value)
                            .size(30.dp),
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}

fun Modifier.bottomBarBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = 0f
            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)