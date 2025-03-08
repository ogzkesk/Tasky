package com.ogzkesk.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ogzkesk.ui.resource.SnackBarMessage
import com.ogzkesk.ui.theme.CustomTypography
import com.ogzkesk.ui.theme.GreenLight
import com.ogzkesk.ui.theme.RedLight
import com.ogzkesk.ui.theme.asSemiBold
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSnackBar(
    data: SnackBarMessage?,
    onDismiss: () -> Unit,
) {
    LaunchedEffect(key1 = data) {
        when (data?.duration) {
            SnackbarDuration.Short -> {
                delay(3000L)
                onDismiss()
            }

            SnackbarDuration.Long -> {
                delay(6000L)
                onDismiss()
            }

            else -> {
            }
        }
    }

    AnimatedVisibility(
        visible = data != null,
        enter = scaleIn(initialScale = 0.6F) + fadeIn(),
        exit = scaleOut() + fadeOut(),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Snackbar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .systemBarsPadding()
                    .padding(
                        top = TopAppBarDefaults.windowInsets
                            .asPaddingValues()
                            .calculateTopPadding(),
                    ),
                containerColor = when (data?.type) {
                    SnackBarMessage.Type.Positive -> GreenLight
                    SnackBarMessage.Type.Negative -> RedLight
                    else -> SnackbarDefaults.color
                },
                dismissAction = {
                    if (data?.dismissAction != null) {
                        IconButton(
                            onClick = {
                                data.onDismiss?.invoke()
                                onDismiss()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                            )
                        }
                    }
                },
                action = {
                    if (data?.actionLabel != null) {
                        TextButton(
                            onClick = {
                                data.onAction?.invoke()
                                onDismiss()
                            },
                            shape = com.ogzkesk.ui.theme.shapes.small,
                            colors =
                                ButtonDefaults.textButtonColors(
                                    contentColor = Color.White,
                                ),
                        ) {
                            Text(
                                text = data.actionLabel,
                                style = CustomTypography.H5.asSemiBold(),
                            )
                        }
                    }
                },
                content = {
                    data?.message?.let {
                        Text(text = it, style = CustomTypography.H6)
                    }
                },
            )
        }
    }
}
