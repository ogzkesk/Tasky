package com.ogzkesk.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ogzkesk.ui.R
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.util.ThemedPreviews

@Composable
fun TaskyAlertDialog(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit,
) {
    if (enabled) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {},
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = stringResource(R.string.dialog_confirm_button_text))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.dialog_cancel_button_text))
                }
            }
        )
    }
}

@ThemedPreviews
@Composable
private fun TaskyAlertDialogPreview() {
    TaskyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TaskyAlertDialog(
                enabled = true,
                title = "Title",
                message = "Message",
                onDismiss = {},
                onConfirm = {},
            )
        }
    }
}