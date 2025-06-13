package com.example.dummyui.ui.presentation


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun BackgroundPattern(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF121212),
    patternColor: Color = Color.White
) {
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.99f)
        ) {
            drawRect(color = backgroundColor)

            val gridSize = 7
            val baseSize = size.width / (gridSize * 1.5f)

            val shapes = mutableListOf<RectangleProps>()

//            shapes.add(RectangleProps(Offset(size.width * 0.6f, size.height * 0.05f), Size(baseSize * 0.9f, baseSize * 0.9f), 0.07f))
//            shapes.add(RectangleProps(Offset(size.width * 0.75f, size.height * 0.05f), Size(baseSize * 0.8f, baseSize * 0.8f), 0.05f))
//            shapes.add(RectangleProps(Offset(size.width * 0.85f, size.height * 0.05f), Size(baseSize * 0.7f, baseSize * 0.7f), 0.09f))
//            shapes.add(RectangleProps(Offset(size.width * 0.6f, size.height * 0.15f), Size(baseSize * 0.85f, baseSize * 0.85f), 0.04f))
//            shapes.add(RectangleProps(Offset(size.width * 0.75f, size.height * 0.15f), Size(baseSize * 1.2f, baseSize * 0.65f), 0.08f))
//            shapes.add(RectangleProps(Offset(size.width * 0.45f, size.height * 0.1f), Size(baseSize * 0.9f, baseSize * 0.9f), 0.05f))
//            shapes.add(RectangleProps(Offset(size.width * 0.35f, size.height * 0.05f), Size(baseSize * 0.7f, baseSize * 0.7f), 0.03f))
//            shapes.add(RectangleProps(Offset(size.width * 0.15f, size.height * 0.08f), Size(baseSize * 0.65f, baseSize * 0.65f), 0.02f))
//            shapes.add(RectangleProps(Offset(size.width * 0.05f, size.height * 0.13f), Size(baseSize * 0.75f, baseSize * 0.75f), 0.04f))
//            shapes.add(RectangleProps(Offset(size.width * 0.85f, size.height * 0.25f), Size(baseSize * 0.85f, baseSize * 0.65f), 0.05f))
//            shapes.add(RectangleProps(Offset(size.width * 0.8f, size.height * 0.35f), Size(baseSize * 0.7f, baseSize * 0.9f), 0.03f))

            for (row in 0 until 3) {
                for (col in 3 until 6) {
                    if ((row + col) % 3 != 0) continue
                    val x = size.width * (0.2f + col * 0.15f)
                    val y = size.height * (0.3f + row * 0.1f)
                    val rectSize = baseSize * (0.5f + (row + col) % 2 * 0.2f)
                    val alpha = 0.01f + (row + col) % 3 * 0.01f
                    shapes.add(RectangleProps(Offset(x, y), Size(rectSize, rectSize), alpha))
                }
            }

            shapes.forEach { rect ->
                drawRect(
                    color = patternColor.copy(alpha = rect.alpha),
                    topLeft = rect.topLeft,
                    size = rect.size
                )
            }
        }
    }
}

private data class RectangleProps(
    val topLeft: Offset,
    val size: Size,
    val alpha: Float
)
