package de.mlex.spacefckrs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.ui.elements.BottomBar
import de.mlex.spacefckrs.ui.elements.PlayFieldGrid
import de.mlex.spacefckrs.ui.elements.TopBar
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
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
        ) {
            PlayFieldGrid()
        }
    }
}



