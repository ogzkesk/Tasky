package com.ogzkesk.tasky.ui.settings.dialog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ogzkesk.tasky.R
import com.ogzkesk.ui.theme.materialColors

@Composable
fun ColorPickerDialog(
    enabled: Boolean,
    currentPrimaryColor: Color?,
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    if (enabled) {
        var selectedColor by remember { mutableStateOf(currentPrimaryColor) }
        var selectedBaseColor by remember { mutableStateOf<Color?>(null) }
        var colorShades by remember { mutableStateOf<List<Color>>(emptyList()) }
        var hexValue by remember { mutableStateOf("") }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Select Primary Color",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5), modifier = Modifier.height(200.dp)
                    ) {
                        items(materialColors) { color ->
                            Box(modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = 2.dp,
                                    color = if (selectedBaseColor == color) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedBaseColor = color
                                    colorShades = getColorShades(color)
                                    selectedColor = null
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedBaseColor != null) {
                        Text(
                            text = "Color Shades",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(colorShades) { shade ->
                                Box(modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(shade)
                                    .border(
                                        width = 2.dp,
                                        color = if (selectedColor == shade) Color.Black else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        selectedColor = shade
                                        hexValue = colorToHex(shade)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedColor != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(selectedColor!!)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "Selected Color",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Text(
                                    text = hexValue, style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(R.string.settings_screen_cancel_button_text))
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                selectedColor?.let { onColorSelected(it) }
                                onDismiss()
                            }, enabled = selectedColor != null
                        ) {
                            Text(stringResource(R.string.settings_screen_apply_button_text))
                        }
                    }
                }
            }
        }
    }
}

private fun getColorShades(baseColor: Color): List<Color> {
    val shades = mutableListOf<Color>()
    val r = baseColor.red
    val g = baseColor.green
    val b = baseColor.blue

    val cMax = maxOf(r, g, b)
    val cMin = minOf(r, g, b)

    val lightness = (cMax + cMin) / 2

    for (i in 0 until 5) {
        val targetLightness = 0.3f + (i * 0.1f)
        val scaleFactor = if (lightness != 0f) targetLightness / lightness else 1f

        val newR = (r * scaleFactor).coerceIn(0f, 1f)
        val newG = (g * scaleFactor).coerceIn(0f, 1f)
        val newB = (b * scaleFactor).coerceIn(0f, 1f)

        shades.add(Color(newR, newG, newB))
    }

    return shades
}

private fun colorToHex(color: Color): String {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    return String.format("#%02X%02X%02X", red, green, blue)
}