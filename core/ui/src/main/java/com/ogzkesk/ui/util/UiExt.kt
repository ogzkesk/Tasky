package com.ogzkesk.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Modifier.conditional(
    condition: Boolean,
    block: @Composable Modifier.() -> Modifier,
): Modifier {
    return if (condition) block.invoke(this) else this
}

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@Preview(uiMode = 16)
@Preview(uiMode = 32)
annotation class ThemedPreviews
