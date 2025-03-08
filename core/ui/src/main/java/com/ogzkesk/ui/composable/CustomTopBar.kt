package com.ogzkesk.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ogzkesk.ui.theme.asSemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    divider: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.asSemiBold(),
    onBackPressed: (() -> Unit)? = null,
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    style = textStyle,
                )
            },
            navigationIcon = {
                if (onBackPressed != null) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onBackPressed() }
                            .padding(4.dp),
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "back",
                    )
                }
            },
        )
        if (divider) {
            HorizontalDivider(thickness = (0.7).dp)
        }
    }
}
