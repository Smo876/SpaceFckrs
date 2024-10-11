package de.mlex.spacefckrs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.ui.elements.BottomBar
import de.mlex.spacefckrs.ui.elements.PlayFieldGrid
import de.mlex.spacefckrs.ui.elements.TopBar
import de.mlex.spacefckrs.ui.theme.SpaceFckrsTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<SpaceViewModel>()

        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            SpaceFckrsTheme {
                // A surface container using the 'background' color from the theme
                ScreenSpaceFckrs(viewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenSpaceFckrs(viewModel: SpaceViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val aliens: List<Alien> = remember { viewModel.aliens }
        //val aliens = viewModel.aliens
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar() { viewModel.createNewRowOfAliens() } },
        ) {
            PlayFieldGrid(aliens)
        }
    }
}
