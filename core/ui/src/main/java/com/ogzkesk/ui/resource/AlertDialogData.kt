package com.ogzkesk.ui.resource

data class AlertDialogData(
    val message: String,
    val title: String? = null,
    val cancelCallback: (() -> Unit)? = null,
    val confirmCallback: (() -> Unit)? = null,
)
