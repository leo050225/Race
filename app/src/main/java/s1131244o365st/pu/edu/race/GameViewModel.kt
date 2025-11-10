package s1131244o365st.pu.edu.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var screenWidthPx by mutableStateOf(0f)
        private set
    var screenHeightPx by mutableStateOf(0f)
        private set

    var circleX by mutableStateOf(100f)
    var circleY by mutableStateOf(100f)
    var frameIndex by mutableStateOf(0)

    var gameRunning by mutableStateOf(false)

    fun StartGame() {
        circleX = 100f
        circleY = screenHeightPx - 300f
        gameRunning = true

        viewModelScope.launch {
            while (gameRunning) {
                delay(100)
                circleX += 10f
                frameIndex = (frameIndex + 1) % 4 // 切換圖片幀

                if (circleX >= screenWidthPx - 200f) {
                    circleX = 100f
                }
            }
        }
    }

    fun StopGame() {
        gameRunning = false
    }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }

    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }
}
