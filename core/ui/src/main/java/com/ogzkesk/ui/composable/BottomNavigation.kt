package com.ogzkesk.ui.composable

import androidx.annotation.FloatRange
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = BottomNavigationDefaults.windowInsets,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    divider: @Composable (() -> Unit)? = { HorizontalDivider(thickness = BottomNavigationDefaults.DividerThickness) },
    tonalElevation: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier,
    ) {
        divider?.let {
            Column {
                divider()
                Row(
                    Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(windowInsets)
                        .defaultMinSize(minHeight = BottomNavigationDefaults.Height)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = content,
                )
            }
        } ?: Row(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .defaultMinSize(minHeight = BottomNavigationDefaults.Height)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content,
        )
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    selectedContentColor: Color = BottomNavigationDefaults.selectedContentColor,
    unselectedContentColor: Color = BottomNavigationDefaults.unselectedContentColor,
) {
    val styledLabel: @Composable (() -> Unit)? =
        label?.let {
            @Composable {
                val style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
                ProvideTextStyle(style, content = label)
            }
        }
    // The color of the Ripple should always the selected color, as we want to show the color
    // before the item is considered selected, and hence before the new contentColor is
    // provided by BottomNavigationTransition.
    val ripple = ripple(bounded = false, color = selectedContentColor)

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = ripple,
            )
            .weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        BottomNavigationTransition(
            selectedContentColor,
            unselectedContentColor,
            selected,
        ) { progress ->
            val animationProgress = if (alwaysShowLabel) 1f else progress

            BottomNavigationItemBaselineLayout(
                icon = icon,
                label = styledLabel,
                iconPositionAnimationProgress = animationProgress,
            )
        }
    }
}

@Composable
private fun BottomNavigationTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable (animationProgress: Float) -> Unit,
) {
    val animationProgress by
        animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = BottomNavigationDefaults.AnimationSpec,
            label = "",
        )

    val color = lerp(inactiveColor, activeColor, animationProgress)

    CompositionLocalProvider(
        LocalContentColor provides color,
    ) {
        content(animationProgress)
    }
}

@Composable
private fun BottomNavigationItemBaselineLayout(
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    @FloatRange(from = 0.0, to = 1.0) iconPositionAnimationProgress: Float,
) {
    Layout({
        Box(Modifier.layoutId("icon")) { icon() }
        if (label != null) {
            Box(
                Modifier
                    .layoutId("label")
                    .alpha(iconPositionAnimationProgress)
                    .padding(horizontal = BottomNavigationDefaults.ItemHorizontalPadding),
            ) {
                label()
            }
        }
    }) { measurables, constraints ->
        val iconPlaceable = measurables.fastFirst { it.layoutId == "icon" }.measure(constraints)

        val labelPlaceable =
            label?.let {
                measurables
                    .fastFirst { it.layoutId == "label" }
                    .measure(
                        constraints.copy(minHeight = 0),
                    )
            }

        if (label == null) {
            placeIcon(iconPlaceable, constraints)
        } else {
            placeLabelAndIcon(
                labelPlaceable!!,
                iconPlaceable,
                constraints,
                iconPositionAnimationProgress,
            )
        }
    }
}

private fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    constraints: Constraints,
): MeasureResult {
    val height = constraints.constrainHeight(BottomNavigationDefaults.Height.roundToPx())
    val iconY = (height - iconPlaceable.height) / 2
    return layout(iconPlaceable.width, height) { iconPlaceable.placeRelative(0, iconY) }
}

private fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    constraints: Constraints,
    @FloatRange(from = 0.0, to = 1.0) iconPositionAnimationProgress: Float,
): MeasureResult {
    val firstBaseline = labelPlaceable[FirstBaseline]
    val baselineOffset = BottomNavigationDefaults.CombinedItemTextBaseline.roundToPx()
    val netBaselineAdjustment = baselineOffset - firstBaseline

    val contentHeight = iconPlaceable.height + labelPlaceable.height + netBaselineAdjustment
    val height =
        constraints.constrainHeight(max(contentHeight, BottomNavigationDefaults.Height.roundToPx()))
    val contentVerticalPadding = ((height - contentHeight) / 2).coerceAtLeast(0)

    val unselectedIconY = (height - iconPlaceable.height) / 2
    // Icon should be [contentVerticalPadding] from the top
    val selectedIconY = contentVerticalPadding

    // Label's first baseline should be [baselineOffset] below the icon
    val labelY = selectedIconY + iconPlaceable.height + netBaselineAdjustment

    val containerWidth = max(labelPlaceable.width, iconPlaceable.width)

    val labelX = (containerWidth - labelPlaceable.width) / 2
    val iconX = (containerWidth - iconPlaceable.width) / 2

    // How far the icon needs to move between unselected and selected states
    val iconDistance = unselectedIconY - selectedIconY

    // When selected the icon is above the unselected position, so we will animate moving
    // downwards from the selected state, so when progress is 1, the total distance is 0, and we
    // are at the selected state.
    val offset = (iconDistance * (1 - iconPositionAnimationProgress)).roundToInt()

    return layout(containerWidth, height) {
        if (iconPositionAnimationProgress != 0f) {
            labelPlaceable.placeRelative(labelX, labelY + offset)
        }
        iconPlaceable.placeRelative(iconX, selectedIconY + offset)
    }
}

@Suppress("MagicNumber")
object BottomNavigationDefaults {
    val DividerThickness = (0.7).dp

    val AnimationSpec = TweenSpec<Float>(durationMillis = 300, easing = FastOutSlowInEasing)

    val Height = 56.dp

    val ItemHorizontalPadding = 12.dp

    val CombinedItemTextBaseline = 12.dp

    val selectedContentColor: Color
        @Composable
        get() = LocalContentColor.current

    val unselectedContentColor: Color
        @Composable
        get() =
            selectedContentColor.copy(
                alpha =
                    if (isSystemInDarkTheme().not()) {
                        if (LocalContentColor.current.luminance() > 0.5) {
                            HighContrastContentAlpha.MEDIUM
                        } else {
                            LowContrastContentAlpha.MEDIUM
                        }
                    } else {
                        if (LocalContentColor.current.luminance() < 0.5) {
                            HighContrastContentAlpha.MEDIUM
                        } else {
                            LowContrastContentAlpha.MEDIUM
                        }
                    },
            )

    val windowInsets: WindowInsets
        @Composable
        get() =
            WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom,
            )

    private object HighContrastContentAlpha {
        const val HIGH: Float = 1.00f
        const val MEDIUM: Float = 0.74f
        const val DISABLED: Float = 0.38f
    }

    private object LowContrastContentAlpha {
        const val HIGH: Float = 0.87f
        const val MEDIUM: Float = 0.60f
        const val DISABLED: Float = 0.38f
    }
}
