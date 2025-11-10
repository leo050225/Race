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

    var gameRunning by mutableStateOf(false)

    // 加上圓圈座標狀態
    var circleX by mutableStateOf(100f)
    var circleY by mutableStateOf(100f)

    fun StartGame() {
        // 回到初始位置
        circleX = 100f
        circleY = screenHeightPx - 100f

        gameRunning = true

        viewModelScope.launch {
            while (gameRunning) { // 每 0.1 秒循環
                delay(100)
                circleX += 10f

                if (circleX >= screenWidthPx - 100f) {
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

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }
}
