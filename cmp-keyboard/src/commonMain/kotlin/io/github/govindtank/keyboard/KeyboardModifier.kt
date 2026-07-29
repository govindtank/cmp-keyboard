package io.github.govindtank.keyboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Adds bottom padding equal to the keyboard height when the keyboard is visible.
 *
 * Use on scrollable content or forms where the bottom of the layout should
 * stay above the keyboard.
 *
 * @param keyboardInfo The current keyboard state (obtained from [rememberKeyboardInfo]).
 */
fun Modifier.keyboardPadding(keyboardInfo: KeyboardInfo): Modifier = composed {
    if (keyboardInfo.isVisible && keyboardInfo.height > 0.dp) {
        this.then(KeyboardPaddingModifier(keyboardInfo.height))
    } else {
        this
    }
}

private class KeyboardPaddingModifier(
    private val height: Dp
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        val bottomPadding = height.roundToPx()
        return layout(placeable.width, placeable.height + bottomPadding) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun hashCode(): Int = height.hashCode()
    override fun equals(other: Any?): Boolean =
        other is KeyboardPaddingModifier && other.height == height
}
