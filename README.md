# cmp-keyboard

<p align="center">
  <a href="https://jitpack.io/#govindtank/cmp-keyboard"><img src="https://jitpack.io/v/govindtank/cmp-keyboard.svg?style=flat-square" alt="JitPack"></a>
  <a href="https://github.com/govindtank/cmp-keyboard/actions"><img src="https://img.shields.io/github/actions/workflow/status/govindtank/cmp-keyboard/build.yml?branch=main&style=flat-square&label=build" alt="Build Status"></a>
  <img src="https://img.shields.io/badge/Platform-Android%20%7C%20iOS%20%7C%20CMP-blue?style=flat-square" alt="Platform">
  <img src="https://img.shields.io/badge/Kotlin-2.0.0-purple?style=flat-square" alt="Kotlin">
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg?style=flat-square" alt="License"></a>
  <a href="https://github.com/govindtank"><img src="https://img.shields.io/badge/Author-Govind%20Tank-orange?style=flat-square" alt="Author"></a>
</p>

<p align="center">
  <b>Compose Multiplatform reactive keyboard-aware layout engine.</b><br>
  <i>Architected &amp; Crafted with ❤️ by <a href="https://github.com/govindtank">Govind Tank</a></i>
</p>

<p align="center">
  <img src="./screenshot.svg" width="750" alt="cmp-keyboard before/after comparison" style="border-radius: 14px;" />
</p>

---

## ⚡ Why `cmp-keyboard`?

Compose Multiplatform has historically lacked consistent software keyboard handling across mobile platforms:
- **Android** — `Modifier.imePadding()` behaves inconsistently on foldables, landscape modes, and edge-to-edge gesture navigation.
- **iOS** — The software keyboard overlays Compose content entirely by default without automatic insets.

`cmp-keyboard` solves this with **one declarative composable** that works seamlessly everywhere.

---

## 📦 Installation

Add the JitPack repository and dependency to your `build.gradle.kts`:

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.govindtank:cmp-keyboard:1.0.0")
}
```

---

## 🚀 Quick Start

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import io.github.govindtank.keyboard.KeyboardAware
import io.github.govindtank.keyboard.rememberKeyboardInfo

@Composable
fun ChatScreen() {
    val keyboard by rememberKeyboardInfo()
    var text by remember { mutableStateOf("") }

    KeyboardAware {
        Column {
            LazyColumn(modifier = Modifier.weight(1f)) {
                // message list
            }
            TextField(value = text, onValueChange = { text = it })
            Button(onClick = { /* send */ }) { 
                Text("Send") 
            }
        }
    }

    if (keyboard.isVisible) {
        Text("Keyboard Height: ${keyboard.height}")
    }
}
```

---

## 📱 Platform Support

| Platform | Detection Mechanism | Notes |
| :--- | :--- | :--- |
| **Android** | `ViewTreeObserver.OnGlobalLayoutListener` | Works on foldables, landscape, edge-to-edge |
| **iOS** | `UIResponder.keyboardWillShowNotification` / `keyboardWillHideNotification` | Uses safe-area insets (iOS 15+) |

---

## 💖 Support & Sponsorship

If you find this library helpful for your Compose Multiplatform applications, consider supporting continuous development:

<p align="left">
  <a href="https://www.patreon.com/govindtank"><img src="https://img.shields.io/badge/Patreon-Support%20Creator-F96854?style=for-the-badge&logo=patreon&logoColor=white" alt="Patreon"></a>
  <a href="https://github.com/sponsors/govindtank"><img src="https://img.shields.io/badge/GitHub%20Sponsors-Sponsor-EA4AAA?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Sponsors"></a>
  <a href="https://buymeacoffee.com/govindtank"><img src="https://img.shields.io/badge/Buy%20Me%20A%20Coffee-Donate-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black" alt="Buy Me A Coffee"></a>
</p>

- **Patreon**: [patreon.com/govindtank](https://www.patreon.com/govindtank)
- **GitHub Sponsors**: [github.com/sponsors/govindtank](https://github.com/sponsors/govindtank)
- **Buy Me a Coffee**: [buymeacoffee.com/govindtank](https://buymeacoffee.com/govindtank)

---

## 👨💻 Author

**Govind Tank**
- **GitHub**: [@govindtank](https://github.com/govindtank)
- **Website**: [govindtank.github.io](https://govindtank.github.io)
- **LinkedIn**: [linkedin.com/in/govind-tank](https://linkedin.com/in/govind-tank)

---

## 📄 License

Apache License 2.0
