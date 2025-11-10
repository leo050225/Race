package s1131244o365st.pu.edu.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class Horse(
    var x: Float,
    var y: Float,
    var frame: Int = 0
)

class GameViewModel : ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set
    var screenHeightPx by mutableStateOf(0f)
        private set

    // 三匹馬
    var horses by mutableStateOf(
        listOf(
            Horse(0f, 200f),
            Horse(0f, 400f),
            Horse(0f, 600f)
        )
    )

    var winnerText by mutableStateOf("")
    var gameRunning by mutableStateOf(false)

    fun StartGame() {
        if (gameRunning) return
        gameRunning = true
        winnerText = ""

        viewModelScope.launch {
            while (gameRunning) {
                delay(100)
                horses = horses.mapIndexed { index, horse ->
                    val step = Random.nextInt(10, 30)
                    var newX = horse.x + step
                    var newFrame = (horse.frame + 1) % 4
                    Horse(newX, horse.y, newFrame)
                }

                // 判斷是否有馬抵達終點
                val winnerIndex = horses.indexOfFirst { it.x >= screenWidthPx - 200f }
                if (winnerIndex != -1) {
                    gameRunning = false
                    winnerText = "第${winnerIndex + 1}馬獲勝！"

                    // 2 秒後重新開始
                    viewModelScope.launch {
                        delay(2000)
                        ResetGame()
                        StartGame()
                    }
                }
            }
        }
    }

    fun ResetGame() {
        horses = listOf(
            Horse(0f, 200f),
            Horse(0f, 400f),
            Horse(0f, 600f)
        )
        winnerText = ""
    }

    fun StopGame() {
        gameRunning = false
    }

    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        if (!gameRunning) StartGame()
    }
}
