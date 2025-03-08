package com.ogzkesk.ui.resource

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val DefaultDurationMs = 300
private const val DefaultScale = 0.8F

data class ScreenTransition(
    val enterTransition: EnterTransition? = null,
    val exitTransition: ExitTransition? = null,
    val popEnterTransition: EnterTransition? = null,
    val popExitTransition: ExitTransition? = null,
) {
    companion object {
        fun default(): ScreenTransition {
            return ScreenTransition(
                enterTransition = null,
                exitTransition = null,
                popEnterTransition = null,
                popExitTransition = null,
            )
        }

        fun fade(duration: Int = DefaultDurationMs): ScreenTransition {
            return ScreenTransition(
                enterTransition = fadeIn(tween(duration)),
                exitTransition = fadeOut(tween(duration)),
            )
        }

        fun slide(): ScreenTransition {
            return ScreenTransition(
                enterTransition = slideInEnter(),
                exitTransition = slideOutExit(),
                popExitTransition = slideOutPopExit(),
                popEnterTransition = slideInPopEnter(),
            )
        }

        fun scale(): ScreenTransition {
            return ScreenTransition(
                enterTransition = scaleInEnter(),
                exitTransition = scaleOutExit(),
            )
        }

        private fun slideInEnter(duration: Int = DefaultDurationMs): EnterTransition {
            return slideInHorizontally(
                animationSpec = tween(duration),
                initialOffsetX = { it / 2 },
            ) + fadeIn(tween(duration))
        }

        private fun slideOutExit(duration: Int = DefaultDurationMs): ExitTransition {
            return slideOutHorizontally(animationSpec = tween(duration)) +
                fadeOut(tween(duration))
        }

        private fun slideInPopEnter(duration: Int = DefaultDurationMs): EnterTransition {
            return slideInHorizontally(animationSpec = tween(duration)) +
                fadeIn(tween(duration))
        }

        private fun slideOutPopExit(duration: Int = DefaultDurationMs): ExitTransition {
            return slideOutHorizontally(
                animationSpec = tween(duration),
                targetOffsetX = { it / 2 },
            ) + fadeOut(tween(duration))
        }

        private fun scaleInEnter(
            duration: Int = DefaultDurationMs,
            scale: Float = DefaultScale,
        ): EnterTransition {
            return scaleIn(animationSpec = tween(duration), initialScale = scale) +
                fadeIn(tween(duration))
        }

        private fun scaleOutExit(
            duration: Int = DefaultDurationMs,
            scale: Float = DefaultScale,
        ): ExitTransition {
            return scaleOut(animationSpec = tween(duration), targetScale = scale) +
                fadeOut(tween(duration))
        }
    }
}
