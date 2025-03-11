package com.ogzkesk.tasky.ui.home.content

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.theme.semiBold
import kotlin.math.roundToInt

@Composable
fun HomeSpotlight(
    modifier: Modifier = Modifier,
    bounds: Rect,
    onConfirm: () -> Unit
) {
    SpotlightBackground(
        bounds = bounds
    )
    SpotLightDialog(
        modifier = modifier,
        bounds = bounds,
        onConfirm = onConfirm
    )
}

@Composable
fun SpotlightBackground(
    modifier: Modifier = Modifier,
    bounds: Rect,
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        with(drawContext.canvas.nativeCanvas) {
            val saveCount = saveLayer(null, null)
            drawRect(color = Color.Black.copy(alpha = 0.8f))

            val maskPaint = Paint().apply {
                color = Color(0f, 0f, 0f, 0f)
                blendMode = BlendMode.Clear
            }

            drawRoundRect(
                bounds.toAndroidRectF().apply { inset(-40f, -40f) },
                30f,
                30f,
                maskPaint.asFrameworkPaint(),
            )
            restoreToCount(saveCount)
        }
    }
}

@Composable
private fun SpotLightDialog(
    modifier: Modifier = Modifier,
    bounds: Rect,
    onConfirm: () -> Unit
) {
    var isInitialized: Boolean by remember {
        mutableStateOf(false)
    }
    var dialogSize: IntSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    Column(
        modifier = modifier
            .width(200.dp)
            .offset {
                if (dialogSize == IntSize.Zero) {
                    IntOffset(0, 0)
                } else {
                    isInitialized = true
                    val rectF = bounds.toAndroidRectF()
                    rectF.inset(-48f, -48f)
                    IntOffset(
                        x = (rectF.centerX() - dialogSize.width / 2 - 170).roundToInt(),
                        y = (rectF.top - (dialogSize.height + 120F)).roundToInt(),
                    )
                }
            }
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(20f, 20f),
                )
            }
            .padding(vertical = 16.dp)
            .onSizeChanged {
                if (dialogSize == IntSize.Zero) {
                    dialogSize = it
                }
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.home_screen_spotlight_title),
            style = MaterialTheme.typography.bodyLarge.semiBold.copy(
                color = Color.Black
            ),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(R.drawable.ic_empty),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(160.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.home_screen_spotlight_description),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp)
                .clip(CircleShape)
                .clickable { onConfirm() }
                .padding(horizontal = 8.dp),
            text = stringResource(id = R.string.home_screen_spotlight_confirm_button_text),
            style = MaterialTheme.typography.titleMedium.semiBold,
            color = Color.Black,
        )
    }
}
