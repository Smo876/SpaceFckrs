package de.mlex.spacefckrs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import de.mlex.spacefckrs.ui.theme.SpaceFckrsTheme
import kotlinx.coroutines.delay


@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceFckrsTheme {
                SplashScreen()
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreen() {
    LaunchedEffect(key1 = true) {
        delay(2000)
        val i = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(i)
    }

}
