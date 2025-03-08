package com.ogzkesk.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class Shapes(
    val default: Shape = RectangleShape,
    val circle: Shape = CircleShape,
    val extraSmall: Shape = RoundedCornerShape(4.dp),
    val small: Shape = RoundedCornerShape(8.dp),
    val medium: Shape = RoundedCornerShape(16.dp),
    val large: Shape = RoundedCornerShape(24.dp),
    val extraLarge: Shape = RoundedCornerShape(32.dp),
    val card: Shape = RoundedCornerShape(10.dp),
    val dialog: Shape = RoundedCornerShape(14.dp),
    val bottomSheet: Shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp),
)

val LocalShapes = staticCompositionLocalOf { Shapes() }

val shapes: Shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current
