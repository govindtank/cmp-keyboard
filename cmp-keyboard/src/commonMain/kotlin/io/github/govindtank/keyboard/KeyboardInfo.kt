package io.github.govindtank.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Represents the current state of the software keyboard.
 *
 * @param isVisible Whether the keyboard is currently shown.
 * @param height The height of the visible keyboard portion in Density-independent pixels.
 * @param animationDurationMs The duration of the keyboard show/hide animation in milliseconds.
 */
data class KeyboardInfo(
    val isVisible: Boolean,
    val height: Dp,
    val animationDurationMs: Long
) {
    companion object {
        /** Empty/hidden keyboard state — no keyboard visible. */
        val Hidden = KeyboardInfo(false, 0.dp, 0L)
    }
}

/**
 * Platform-specific composable that observes the software keyboard state.
 *
 * Returns a [State]<[KeyboardInfo]> that updates when the keyboard appears or disappears.
 * The value is [KeyboardInfo.Hidden] when no keyboard is visible.
 *
 * Usage:
 * ```kotlin
 * val keyboard by rememberKeyboardInfo()
 * if (keyboard.isVisible) {
 *     // Keyboard is open, adjust layout
 * }
 * ```
 */
@Composable
expect fun rememberKeyboardInfo(): State<KeyboardInfo>
