package s1131244o365st.pu.edu.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator
import s1131244o365st.pu.edu.race.ui.theme.RaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 強迫橫式螢幕
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // 隱藏狀態列
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowMetricsCalculator = WindowMetricsCalculator.getOrCreate()
        val currentWindowMetrics = windowMetricsCalculator.computeCurrentWindowMetrics(this)
        val bounds = currentWindowMetrics.bounds
        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()

        val gameViewModel: GameViewModel by viewModels()
        gameViewModel.SetGameSize(screenWidthPx, screenHeightPx)

        setContent {
            RaceTheme {
                GameScreen(message = "賽馬遊戲(作者：資管二A 411312448 施聿觀)", viewModel = gameViewModel)
            }
        }
    }
}
