package s1131244o365st.pu.edu.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(message: String, viewModel: GameViewModel = viewModel()) {

    val circleX = viewModel.circleX
    val circleY = viewModel.circleY

    val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

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
                        change.consume() // 告訴系統已經處理這個事件
                        viewModel.MoveCircle(dragAmount.x, dragAmount.y)
                    }
                }
        ) {
            // 繪製圓形
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(circleX, circleY)
            )

            drawImage(
                image = imageBitmap,
                dstOffset = IntOffset(0, 100),
                dstSize = IntSize(200, 200)
            )

        }
    }
}
