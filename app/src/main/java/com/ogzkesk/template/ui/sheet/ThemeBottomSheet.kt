package com.ogzkesk.template.ui.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ogzkesk.domain.prefs.model.Theme
import com.ogzkesk.ui.resource.BottomSheetData
import com.ogzkesk.ui.resource.ThemeManager
import com.ogzkesk.ui.theme.CustomTypography
import com.ogzkesk.ui.theme.asSemiBold

@Composable
fun ThemeBottomSheet(sheetData: BottomSheetData.ThemeData) {
    var selectedIndex by remember {
        mutableIntStateOf(
            when (ThemeManager.theme.value) {
                Theme.SYSTEM -> 0
                Theme.DARK -> 1
                Theme.LIGHT -> 2
                else -> 0
            },
        )
    }

    val items = listOf(
        "System Default",
        "Dark Mode",
        "Light Mode",
    )

    Column(Modifier.padding(bottom = 24.dp)) {
        items.forEachIndexed { index, s ->
            ItemContainer(
                selectedIndex = selectedIndex,
                index = index,
                title = s,
                onClick = {
                    selectedIndex = it
                    when (index) {
                        0 -> sheetData.onThemeSelected(Theme.SYSTEM)
                        1 -> sheetData.onThemeSelected(Theme.DARK)
                        2 -> sheetData.onThemeSelected(Theme.LIGHT)
                    }
                },
            )
        }
    }
}

@Composable
private fun ItemContainer(
    selectedIndex: Int,
    index: Int,
    title: String,
    onClick: (index: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(index)
            }
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = index == selectedIndex, onClick = null)
        Text(text = title, style = CustomTypography.H6.asSemiBold())
    }
}
