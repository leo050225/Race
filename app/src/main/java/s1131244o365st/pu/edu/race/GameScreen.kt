package s1131244o365st.pu.edu.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
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

@Composable
fun GameScreen(message: String, viewModel: GameViewModel = viewModel()) {

    val horses = viewModel.horses
    val winnerText = viewModel.winnerText
    var gameRunning by remember { mutableStateOf(false) }

    // 紅色可拖曳按鈕的位置
    var buttonX by remember { mutableStateOf(200f) }
    var buttonY by remember { mutableStateOf(100f) }

    // 馬的動畫圖片
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176))
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
                drawImage(
                    image = imageBitmaps[horse.frame],
                    dstOffset = IntOffset(horse.x.toInt(), horse.y.toInt()),
                    dstSize = IntSize(200, 200)
                )
            }
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = if (gameRunning) "遊戲停止" else "遊戲開始",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
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
