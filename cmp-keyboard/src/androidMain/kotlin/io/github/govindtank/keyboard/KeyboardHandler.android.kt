package io.github.govindtank.keyboard

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
actual fun rememberKeyboardInfo(): State<KeyboardInfo> {
    val view = LocalView.current
    val density = LocalDensity.current
    val state = remember { mutableStateOf(KeyboardInfo.Hidden) }

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)

            val screenHeight = view.height
            val keyboardHeightPx = screenHeight - rect.bottom

            val isVisible = keyboardHeightPx > screenHeight * 0.15

            val heightDp = with(density) { keyboardHeightPx.toDp() }
            state.value = KeyboardInfo(
                isVisible = isVisible,
                height = if (isVisible) heightDp else 0.dp,
                animationDurationMs = 300L
            )
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return state
}
