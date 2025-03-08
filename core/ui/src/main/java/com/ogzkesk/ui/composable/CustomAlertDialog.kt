package com.ogzkesk.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ogzkesk.ui.R
import com.ogzkesk.ui.resource.AlertDialogData
import com.ogzkesk.ui.theme.CustomTypography
import com.ogzkesk.ui.theme.contentBlue
import com.ogzkesk.ui.theme.shapes

@Composable
fun CustomAlertDialog(
    data: AlertDialogData?,
    onConfirm: () -> Unit,
) {
    data?.let {
        AlertDialog(
            onDismissRequest = {},
            title = {
                data.title?.let {
                    Text(
                        text = data.title,
                        style = CustomTypography.H5,
                        textAlign = TextAlign.Center,
                    )
                }
            },
            text = {
                Text(
                    text = data.message,
                    style = CustomTypography.P14,
                    textAlign = TextAlign.Center,
                )
            },
            dismissButton = {
                data.cancelCallback?.let {
                    TextButton(
                        onClick = { it.invoke() },
                        shape = shapes.card,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        data.confirmCallback?.invoke()
                        onConfirm()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.contentBlue,
                    ),
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
        )
    }
}
