package s1131244o365st.pu.edu.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(message: String, viewModel: GameViewModel = viewModel()) {
    // 觀察 ViewModel 狀態
    val circleX = viewModel.circleX
    val circleY = viewModel.circleY
    val frame = viewModel.frameIndex

    // 載入馬的動畫圖片
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(text = message)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        viewModel.MoveCircle(dragAmount.x, dragAmount.y)
                    }
                }
        ) {
            // 繪製馬的動畫
            drawImage(
                image = imageBitmaps[frame],
                dstOffset = IntOffset(circleX.toInt(), circleY.toInt()),
                dstSize = IntSize(200, 200)
            )
        }
    }
}
