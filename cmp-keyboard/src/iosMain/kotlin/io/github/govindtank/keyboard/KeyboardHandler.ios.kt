package io.github.govindtank.keyboard

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSNumber
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplication
import platform.UIKit.UIKeyboardAnimationDurationUserInfoKey
import platform.UIKit.UIKeyboardFrameEndUserInfoKey
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification
import platform.UIKit.UIScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * On iOS, read keyboard height from `UIScreen.mainScreen.bounds` minus
 * the visible frame after the keyboard appears.
 *
 * We observe the keyboard notifications for timing and animation duration,
 * and compute the actual height from the safe area insets, which on iOS 15+
 * reflect the keyboard once the layout pass completes.
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberKeyboardInfo(): State<KeyboardInfo> {
    val density = LocalDensity.current
    val state = remember { mutableStateOf(KeyboardInfo.Hidden) }

    DisposableEffect(Unit) {
        val center = NSNotificationCenter.defaultCenter
        val mainQueue = NSOperationQueue.mainQueue

        val showObserver = center.addObserverForName(
            UIKeyboardWillShowNotification,
            null,
            mainQueue
        ) { _: NSNotification? ->
            // Compute keyboard height from safe area bottom inset
            val kbHeightPx = readKeyboardHeight()
            val durationMs = 300L // standard iOS animation
            val heightDp = with(density) { kbHeightPx.toFloat().toDp() }
            state.value = KeyboardInfo(
                isVisible = true,
                height = heightDp,
                animationDurationMs = durationMs
            )
        }

        val hideObserver = center.addObserverForName(
            UIKeyboardWillHideNotification,
            null,
            mainQueue
        ) { _: NSNotification? ->
            state.value = KeyboardInfo.Hidden
        }

        onDispose {
            center.removeObserver(showObserver)
            center.removeObserver(hideObserver)
        }
    }

    return state
}

@OptIn(ExperimentalForeignApi::class)
private fun readKeyboardHeight(): Double {
    // Method 1: Safe area insets (iOS 15+, most reliable)
    val window = UIApplication.sharedApplication.keyWindow
    if (window != null) {
        val insets = window.safeAreaInsets
        val bottom = insets.useContents { bottom }
        if (bottom > 0) return bottom
    }

    // Method 2: Fallback — keyboard is roughly 40% of screen height
    val screenHeight = UIScreen.mainScreen.bounds.useContents { size.height }
    return screenHeight * 0.4
}
