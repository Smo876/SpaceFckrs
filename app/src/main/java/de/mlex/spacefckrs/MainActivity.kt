package de.mlex.spacefckrs

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.ui.elements.AttackerScreen
import de.mlex.spacefckrs.ui.elements.BottomBar
import de.mlex.spacefckrs.ui.elements.DefenseScreen
import de.mlex.spacefckrs.ui.elements.TopBar
import de.mlex.spacefckrs.ui.theme.SpaceFckrsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<SpaceViewModel>()

        super.onCreate(savedInstanceState)

        //TODO: fullscreen Modus
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 1000L
                zoomX.doOnEnd { screen.remove() }
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 1000L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

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
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                BottomBar(viewModel.nextDamage.intValue)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AttackerScreen(viewModel.aliens.collectAsState())
                DefenseScreen { viewModel.nextMove() }
            }
        }
    }
}
