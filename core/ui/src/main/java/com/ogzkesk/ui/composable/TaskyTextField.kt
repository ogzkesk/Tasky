package com.ogzkesk.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.ogzkesk.ui.theme.placeholder

@Composable
fun TaskyTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean = true,
    isError: Boolean = false,
    placeHolder: String? = null,
    label: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        singleLine = singleLine,
        isError = isError,
        onValueChange = onValueChange,
        placeholder = {
            placeHolder?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.placeholder
                    )
                )
            }
        },
        label = {
            label?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}