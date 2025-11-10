package s1131244o365st.pu.edu.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape // 引入 CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
// 請確保您的 R.drawable.horse0, horse1, horse2, horse3 存在
// 這裡用一個假的 R.drawable 來避免編譯錯誤，但您必須在專案中加入圖片
import s1131244o365st.pu.edu.race.R.drawable

@Composable
fun GameScreen(message: String, viewModel: GameViewModel = viewModel()) {

    val horses = viewModel.horses
    val winnerText = viewModel.winnerText
    var gameRunning by remember { mutableStateOf(false) }

    // 紅色可拖曳按鈕的位置
    var buttonX by remember { mutableStateOf(200f) }
    var buttonY by remember { mutableStateOf(100f) }

    // 馬的動畫圖片
    // ⚠️ 這些圖片 (R.drawable.horse0 到 horse3) 必須在您的專案中存在
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(drawable.horse0),
        ImageBitmap.imageResource(drawable.horse1),
        ImageBitmap.imageResource(drawable.horse2),
        ImageBitmap.imageResource(drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176)) // 黃色背景
    ) {

        // 標題
        Text(
            text = message,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
        )

        // 繪製三匹馬
        Canvas(modifier = Modifier.fillMaxSize()) {
            horses.forEach { horse ->
                // 畫布上繪製圖片
                drawImage(
                    image = imageBitmaps[horse.frame],
                    dstOffset = IntOffset(horse.x.toInt(), horse.y.toInt()),
                    dstSize = IntSize(200, 200) // 假設馬匹圖片大小為 200x200
                )
            }

            // 繪製終點線 (示範)
            val finishLineX = 1000f // 需與 ViewModel 中的 finishLineX 保持一致，但這裡用像素值
            drawLine(
                color = Color.Black,
                start = Offset(finishLineX, 0f),
                end = Offset(finishLineX, size.height),
                strokeWidth = 10f
            )
        }

        // 紅色可拖曳按鈕（控制遊戲）
        Box(
            modifier = Modifier
                .offset { IntOffset(buttonX.toInt(), buttonY.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        buttonX += dragAmount.x
                        buttonY += dragAmount.y
                    }
                }
        ) {
            Button(
                onClick = {
                    if (!gameRunning) {
                        gameRunning = true
                        viewModel.StartGame()
                    } else {
                        gameRunning = false
                        viewModel.StopGame()
                    }
                },
                // ✅ 設置為圓形
                shape = CircleShape,
                // ✅ 設置按鈕大小，確保是圓形
                modifier = Modifier.size(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = if (gameRunning) "遊戲停止" else "遊戲開始",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // 顯示勝利文字
        if (winnerText.isNotEmpty()) {
            Text(
                text = winnerText,
                color = Color.Red,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}