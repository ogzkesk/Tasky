package com.ogzkesk.ui.resource

import androidx.compose.material3.SnackbarDuration

data class SnackBarMessage(
    val message: String,
    val type: Type = Type.Default,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val actionLabel: String? = null,
    val dismissAction: Boolean = true,
    val onDismiss: (() -> Unit)? = null,
    val onAction: (() -> Unit)? = null,
) {
    companion object {
        fun negative(message: String) = SnackBarMessage(message, Type.Negative)

        fun positive(message: String) = SnackBarMessage(message, Type.Positive)

        fun default(message: String) = SnackBarMessage(message, Type.Default)
    }

    enum class Type {
        Positive,
        Negative,
        Default,
    }
}
