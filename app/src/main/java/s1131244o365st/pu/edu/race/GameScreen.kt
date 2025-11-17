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

import s1131244o365st.pu.edu.race.R.drawable

@Composable
fun GameScreen(message: String, viewModel: GameViewModel = viewModel()) {

    val horses = viewModel.horses
    val winnerText = viewModel.winnerText
    var gameRunning by remember { mutableStateOf(false) }

    var buttonX by remember { mutableStateOf(200f) }
    var buttonY by remember { mutableStateOf(100f) }

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
            val finishLineX = 0f
            drawLine(
                color = Color.Black,
                start = Offset(finishLineX, 0f),
                end = Offset(finishLineX, size.height),
                strokeWidth = 10f
            )
        }

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
                shape = CircleShape,
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