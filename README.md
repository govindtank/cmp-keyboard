# cmp-keyboard

<img src="./screenshot.svg" width="750" alt="cmp-keyboard before/after comparison"/>

**Compose Multiplatform reactive keyboard-aware layout.**

A lightweight library that detects the software keyboard on Android and iOS and provides a simple composable API to adjust your layout.

## Why?

Compose Multiplatform has no built-in way to handle the software keyboard across platforms:

- **Android** — `Modifier.imePadding()` exists but is inconsistent on foldables, landscape, and gesture nav
- **iOS** — The keyboard overlays your Compose content entirely. No built-in handling.

`cmp-keyboard` solves this with **one composable** that works everywhere.

## Installation

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

// build.gradle.kts (module)
dependencies {
    implementation("io.github.govindtank:cmp-keyboard:1.0.0")
}
```

## Usage

### Simple wrapper — moves content above keyboard

```kotlin
import io.github.govindtank.keyboard.KeyboardAware
import io.github.govindtank.keyboard.rememberKeyboardInfo

// Option 1: KeyboardAware wrapper
KeyboardAware {
    // Your content automatically stays above the keyboard
    Column {
        TextField(...)
        Button(...)
    }
}

// Option 2: Manual control via state
val keyboard by rememberKeyboardInfo()

Box(
    modifier = Modifier.padding(
        bottom = if (keyboard.isVisible) keyboard.height else 0.dp
    )
) {
    // Your content
}

// Option 3: Modifier approach
val keyboard by rememberKeyboardInfo()
LazyColumn(
    modifier = Modifier.keyboardPadding(keyboard)
) {
    // ...
}
```

### KeyboardInfo properties

| Property             | Description                                    |
|----------------------|------------------------------------------------|
| `isVisible`          | Whether the keyboard is currently shown         |
| `height`             | Keyboard height in `Dp`                        |
| `animationDurationMs`| Animation duration in milliseconds              |

## Platform behavior

| Platform | Detection method                                      |
|----------|-------------------------------------------------------|
| Android  | `ViewTreeObserver.OnGlobalLayoutListener` + visible display frame |
| iOS      | `UIResponder.keyboardWillShowNotification` / `keyboardWillHideNotification` |

## Sample

Check the `sample/` directory for a complete working app with form and chat demos.

## License

Apache 2.0
