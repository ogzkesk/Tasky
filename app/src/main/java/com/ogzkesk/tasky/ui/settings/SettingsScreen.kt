package com.ogzkesk.tasky.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.tasky.R
import com.ogzkesk.tasky.ui.settings.dialog.ThemeDialog
import com.ogzkesk.ui.composable.TaskyTopBar
import com.ogzkesk.ui.theme.TaskyTheme
import com.ogzkesk.ui.theme.semiBold
import com.ogzkesk.ui.util.ThemedPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    state: SettingsScreenState,
    onEvent: (SettingsScreenEvent) -> Unit,
) {
    ThemeDialog(
        currentTheme = state.currentTheme,
        enabled = state.themeDialogState,
        onDismiss = dropUnlessResumed {
            onEvent(SettingsScreenEvent.ToggleThemeDialog(false))
        },
        onThemeSelected = { theme ->
            onEvent(SettingsScreenEvent.OnThemeSelected(theme))
        }
    )

    Scaffold(
        topBar = {
            TaskyTopBar(
                title = stringResource(R.string.settings_screen_title),
                onBack = dropUnlessResumed {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.settings_screen_theme_settings_titlte),
                style = MaterialTheme.typography.titleMedium.semiBold
            )
            SettingRow(
                label = stringResource(R.string.settings_screen_theme_title),
                contentDescription = "theme",
                leadingIcon = Icons.Outlined.Palette,
                onClick = {
                    onEvent(SettingsScreenEvent.ToggleThemeDialog(true))
                }
            )
            SettingRow(
                label = stringResource(R.string.settings_screen_primary_color_title),
                contentDescription = "primary_color",
                leadingIcon = Icons.Outlined.Colorize,
                onClick = {}
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FormatColorFill,
                        contentDescription = "dynamic_colors",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.settings_screen_dynamic_colors_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Switch(
                    modifier = Modifier.height(24.dp),
                    checked = true,
                    onCheckedChange = {}
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun SettingRow(
    modifier: Modifier = Modifier,
    label: String,
    contentDescription: String,
    leadingIcon: ImageVector,
    arrowEnabled: Boolean = true,
    dividerEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        if (arrowEnabled) {
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
    if (dividerEnabled) {
        HorizontalDivider()
    }
}

@ThemedPreviews
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        SettingsScreen(
            navController = rememberNavController(),
            state = SettingsScreenState(),
            onEvent = {}
        )
    }
}