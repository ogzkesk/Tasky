package com.ogzkesk.tasky.ui.home.content

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ogzkesk.tasky.ui.home.HomeScreenEvent
import com.ogzkesk.tasky.ui.home.HomeScreenState

@Composable
fun HomeTabRow(
    modifier: Modifier = Modifier,
    selectedTab: HomeScreenState.HomeTab,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal
    ) {
        HomeScreenState.HomeTab.entries.forEach {
            Tab(
                selected = selectedTab == it,
                onClick = {
                    onEvent(HomeScreenEvent.OnTabSelected(it))
                },
                text = {
                    Text(text = it.name)
                }
            )
        }
    }
}