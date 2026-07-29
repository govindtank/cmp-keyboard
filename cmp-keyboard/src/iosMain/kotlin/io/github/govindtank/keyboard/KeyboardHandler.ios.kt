package io.github.govindtank.keyboard

import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectGetHeight
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSNumber
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSValue
import platform.UIKit.UIResponder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
actual fun rememberKeyboardInfo(): State<KeyboardInfo> {
    val density = LocalDensity.current
    val state = remember { mutableStateOf(KeyboardInfo.Hidden) }

    DisposableEffect(Unit) {
        val center = NSNotificationCenter.defaultCenter
        val mainQueue = NSOperationQueue.mainQueue

        val showObserver = center.addObserverForName(
            UIResponder.keyboardWillShowNotification,
            null,
            mainQueue
        ) { notification ->
            val userInfo = notification.userInfo
            val keyboardValue = userInfo?.get(UIResponder.keyboardFrameEndUserInfoKey) as? NSValue
            val durationNumber = userInfo?.get(UIResponder.keyboardAnimationDurationUserInfoKey) as? NSNumber

            if (keyboardValue != null) {
                val rect = keyboardValue.CGRectValue()
                val heightPx = rect.useContents { size.height }
                val durationMs = ((durationNumber?.doubleValue ?: 0.25) * 1000).toLong()
                val heightDp = with(density) { heightPx.toFloat().toDp() }
                state.value = KeyboardInfo(
                    isVisible = true,
                    height = heightDp,
                    animationDurationMs = durationMs
                )
            }
        }

        val hideObserver = center.addObserverForName(
            UIResponder.keyboardWillHideNotification,
            null,
            mainQueue
        ) { _ ->
            state.value = KeyboardInfo.Hidden
        }

        onDispose {
            center.removeObserver(showObserver)
            center.removeObserver(hideObserver)
        }
    }

    return state
}
