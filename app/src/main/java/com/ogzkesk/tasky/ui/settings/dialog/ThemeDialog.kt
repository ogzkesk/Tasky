package com.ogzkesk.tasky.ui.settings.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ogzkesk.domain.prefs.model.Theme
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.theme.semiBold

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    currentTheme: Theme.Mode,
    enabled: Boolean,
    onDismiss: () -> Unit,
    onThemeSelected: (Theme.Mode) -> Unit
) {
    if (enabled) {
        var selectedTheme by remember { mutableStateOf(currentTheme) }

        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Column(modifier = modifier) {
                    Text(
                        text = stringResource(R.string.settings_dialog_select_theme),
                        style = MaterialTheme.typography.titleSmall.semiBold
                    )
                    Theme.Mode.entries.forEach { theme ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTheme = theme },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = theme.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            RadioButton(
                                selected = selectedTheme == theme,
                                onClick = {
                                    selectedTheme = theme
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onThemeSelected(selectedTheme)
                    },
                ) {
                    Text(text = stringResource(com.ogzkesk.ui.R.string.dialog_confirm_button_text))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(com.ogzkesk.ui.R.string.dialog_cancel_button_text))
                }
            }
        )
    }
}