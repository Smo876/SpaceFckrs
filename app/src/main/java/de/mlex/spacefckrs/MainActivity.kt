package de.mlex.spacefckrs

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.ui.elements.BottomBar
import de.mlex.spacefckrs.ui.elements.GameOverBox
import de.mlex.spacefckrs.ui.elements.GameScreen
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
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ScreenSpaceFckrs(viewModel: SpaceViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = { TopBar(viewModel.score.intValue) },
            bottomBar = { BottomBar(viewModel.nextDamage.intValue) { viewModel.resetGame() } },
        ) {
            if (viewModel.aliens.value.size > 25) GameOverBox(viewModel)
            else {
                GameScreen(viewModel)
            }
        }
    }
}