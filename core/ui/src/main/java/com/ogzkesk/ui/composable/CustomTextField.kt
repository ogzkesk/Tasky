package com.ogzkesk.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes placeHolder: Int? = null,
    @DrawableRes leadingIcon: Int? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textFieldType: TextFieldType = TextFieldType.DEFAULT,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    val focusManager = LocalFocusManager.current
    var passwordToggleState: Boolean by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        enabled = enabled,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        readOnly = readOnly,
        keyboardOptions = if (textFieldType == TextFieldType.PASSWORD) {
            keyboardOptions.copy(keyboardType = KeyboardType.Password)
        } else {
            keyboardOptions
        },
        keyboardActions = keyboardActions,
        visualTransformation =
            if (textFieldType == TextFieldType.PASSWORD && passwordToggleState.not()) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
        textStyle = textStyle.merge(
            TextStyle(
                color =
                    textStyle.color.takeOrElse {
                        colors.focusedTextColor
                    },
            ),
        ),
        cursorBrush = SolidColor(
            colors.cursorColor.takeOrElse {
                MaterialTheme.colorScheme.onBackground
            },
        ),
        decorationBox = { textField ->
            Row(
                modifier = modifier
                    .width(TextFieldDefaults.MinWidth)
                    .height(36.dp)
                    .clip(shape)
                    .background(
                        colors.focusedContainerColor.takeOrElse {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                    )
                    .padding(
                        horizontal = if (shape == CircleShape) 16.dp else 8.dp,
                        vertical = 8.dp,
                    ),
                verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
            ) {
                if (leadingIcon != null) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        painter = painterResource(id = leadingIcon),
                        tint = colors.focusedLeadingIconColor,
                        contentDescription = null,
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (placeHolder != null && value.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(top = if (singleLine.not()) 2.dp else 0.dp),
                            text = stringResource(id = placeHolder),
                            style =
                                textStyle.copy(
                                    color =
                                        colors.focusedPlaceholderColor.takeOrElse {
                                            MaterialTheme.colorScheme.outlineVariant
                                        },
                                ),
                        )
                    }
                    textField.invoke()
                    if (value.isNotEmpty()) {
                        if (textFieldType != TextFieldType.PASSWORD) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clip(CircleShape)
                                    .clickable {
                                        onValueChanged("")
                                        focusManager.clearFocus()
                                    },
                                imageVector = Icons.Rounded.Close,
                                tint = colors.focusedTrailingIconColor,
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clip(CircleShape)
                                    .clickable {
                                        passwordToggleState = !passwordToggleState
                                    },
                                painter = painterResource(
                                    id =
                                        if (passwordToggleState) {
//                                        R.drawable.ic_password_visibility_off
                                            0
                                        } else {
//                                        R.drawable.ic_password_visibility
                                            0
                                        },
                                ),
                                tint = colors.focusedTrailingIconColor,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        },
    )
}

enum class TextFieldType {
    DEFAULT,
    PASSWORD,
}
