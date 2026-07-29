package io.github.govindtank.keyboard

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Wraps content so it's automatically pushed up when the software keyboard appears.
 *
 * Use when you have a full-screen form or text input at the bottom of the screen.
 * The content composable receives the current [KeyboardInfo] in case you need
 * fine-grained control.
 *
 * @param modifier Modifier applied to the container.
 * @param content Content composable that receives keyboard state.
 */
@Composable
fun KeyboardAware(
    modifier: Modifier = Modifier,
    content: @Composable (KeyboardInfo) -> Unit
) {
    val keyboard by rememberKeyboardInfo()

    androidx.compose.foundation.layout.Box(
        modifier = modifier.padding(
            bottom = if (keyboard.isVisible) keyboard.height else 0.dp
        )
    ) {
        content(keyboard)
    }
}

/**
 * Column variant of [KeyboardAware] — provides [ColumnScope] for the content.
 */
@Composable
fun KeyboardAwareColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(KeyboardInfo) -> Unit
) {
    val keyboard by rememberKeyboardInfo()

    androidx.compose.foundation.layout.Column(
        modifier = modifier.padding(
            bottom = if (keyboard.isVisible) keyboard.height else 0.dp
        )
    ) {
        content(keyboard)
    }
}
