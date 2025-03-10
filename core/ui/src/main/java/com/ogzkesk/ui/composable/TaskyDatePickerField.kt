package com.ogzkesk.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import com.ogzkesk.domain.ext.toLocalDateTime
import com.ogzkesk.ui.R
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyDatePickerField(
    modifier: Modifier = Modifier,
    date: Long,
    focusRequester: FocusRequester = remember { FocusRequester() },
    isError: Boolean = false,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit = {}
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var showDatePicker by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    showDatePicker = it.hasFocus
                },
            isError = isError,
            value = if (date == 0L) {
                ""
            } else date
                .toLocalDateTime()
                .format(DateTimeFormatter.ISO_DATE),
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.date_picker_label),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            readOnly = true,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showDatePicker = true
                }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let(onDateSelected)
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.dialog_confirm_button_text))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        onDismiss()
                    }
                ) {
                    Text(text = stringResource(R.string.dialog_cancel_button_text))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
