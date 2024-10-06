package de.mlex.spacefckrs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.ui.theme.SpaceFckrsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            SpaceFckrsTheme {
                // A surface container using the 'background' color from the theme
                ScreenSpaceFckrs()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun ScreenSpaceFckrs() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
//        Scaffold(
//            modifier = Modifier
//                .background(Color.Black),
//            topBar = { TopBar() },
//            bottomBar = { TopBar() },
//        ) {
//            Aliens()
//        }
        Aliens()
    }
}

@Composable
fun Aliens() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Alien(
            color = Color.Red,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Gray,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Green,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Blue,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
        Alien(
            color = Color.Green,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )

    }
}

@Composable
fun Alien(color: Color, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        contentDescription = "Alien",
        painter = painterResource(R.drawable.android_alien),
        colorFilter = ColorFilter.tint(color = color)
    )
}

@Composable
fun GameOverBox() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        ScreenSpaceFckrs()
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .fillMaxSize()
                .background(color = Color.DarkGray.copy(alpha = .8f))
        )
        Text(
            text = "GAME OVER",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SCORE: 0000",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        Text(
            text = "LEVEL: 001",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}


