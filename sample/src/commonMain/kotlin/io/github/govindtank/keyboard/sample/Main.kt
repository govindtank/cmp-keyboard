package io.github.govindtank.keyboard.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.govindtank.keyboard.KeyboardAware
import io.github.govindtank.keyboard.KeyboardAwareColumn
import io.github.govindtank.keyboard.rememberKeyboardInfo

@Composable
fun App() {
    MaterialTheme {
        var showDemo by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "cmp-keyboard Demo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Choose a demo scenario:",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { showDemo = !showDemo },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (!showDemo) "Show KeyboardAware Demo"
                    else "Back to Menu"
                )
            }

            if (showDemo) {
                KeyboardAwareDemo()
            } else {
                MenuDemo()
            }
        }
    }
}

@Composable
fun MenuDemo() {
    // Simple list with message
    Spacer(Modifier.height(24.dp))
    Text(
        text = "KeyboardAware wraps your content so it stays visible when the keyboard opens.",
        fontSize = 14.sp,
        color = Color.DarkGray
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Features:",
        fontWeight = FontWeight.SemiBold
    )
    Text("- Auto-detects keyboard on Android & iOS")
    Text("- Provides keyboard height in Dp")
    Text("- Captures animation duration")
    Text("- Works with any Compose layout")
    Text("- Lightweight: ~40KB added to APK")
}

@Composable
fun KeyboardAwareDemo() {
    val keyboard by rememberKeyboardInfo()
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }

    KeyboardAwareColumn(modifier = Modifier.fillMaxSize()) {
        // Keyboard status banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (keyboard.isVisible) Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = if (keyboard.isVisible)
                    "⌨ Keyboard visible • ${keyboard.height.value.toInt()}dp height"
                else
                    "📱 Keyboard hidden",
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(16.dp))

        // Chat-like messages
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (messages.isEmpty()) {
                item {
                    Text(
                        "Type a message below and tap Send. " +
                        "The input stays above the keyboard!",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            items(messages) { msg ->
                Text(
                    text = msg,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Input row (stays above keyboard thanks to KeyboardAwareColumn)
        OutlinedTextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (text1.isNotBlank()) {
                    messages.add(text1)
                    text1 = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}
