package de.mlex.spacefckrs

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.mlex.spacefckrs.data.Preference
import de.mlex.spacefckrs.ui.elements.BottomBar
import de.mlex.spacefckrs.ui.elements.GameOverBox
import de.mlex.spacefckrs.ui.elements.GameScreen
import de.mlex.spacefckrs.ui.elements.TopBar
import de.mlex.spacefckrs.ui.theme.SpaceFckrsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val spacePreference = Preference(this)
        val viewModel by viewModels<SpaceViewModel>()

        super.onCreate(savedInstanceState)

        //TODO: fullscreen Modus
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.viewModelIsReady.value
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
                //LocalContext.current.  <- zum Messen der Bildschirmgrößte
                ScreenSpaceFckrs(viewModel, spacePreference)

            }
        }
    }
}

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "UnusedBoxWithConstraintsScope"
)
@Composable
fun ScreenSpaceFckrs(viewModel: SpaceViewModel, spacePreference: Preference) {
    val highscore = remember { mutableIntStateOf(spacePreference.getHighScore()) }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = { TopBar(viewModel.score.intValue, highscore.intValue) },
            content = { padding ->
                BoxWithConstraints() {

                    GameScreen(viewModel, padding, maxHeight)
                    val gameState by viewModel.gameState.collectAsState()
                    if (gameState == GameState.GameOver) {
                        GameOverBox()
                    }
                }
            },
            bottomBar = {
                BottomBar(viewModel.nextDamage.intValue) {
                    if (viewModel.score.intValue > highscore.intValue) {
                        highscore.intValue = viewModel.score.intValue
                        spacePreference.setHighScore(viewModel.score.intValue)
                    }
                    viewModel.resetGame()
                }
            },
        )
    }
}