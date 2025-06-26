package com.github.ajalt.mordant.samples

import com.github.ajalt.mordant.input.KeyboardEvent
import com.github.ajalt.mordant.input.interactiveMultiSelectList
import com.github.ajalt.mordant.input.interactiveSelectList
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.danger
import com.github.ajalt.mordant.terminal.success


fun main() {
    val terminal = Terminal()

    val examples = listOf(
        "Pizza Order Example" to ::pizzaOrderExample,
        "Vim Keybindings Demo" to ::vimKeybindingsDemo,
        "Multi-Select Languages" to ::multiSelectLanguagesDemo
    )

    val selected = terminal.interactiveSelectList {
        entries(examples.map { it.first })
        title("Select an example to run:")
    }

    if (selected != null) {
        terminal.println()
        examples.find { it.first == selected }?.second?.invoke()
    } else {
        terminal.danger("No example selected")
    }
}

fun pizzaOrderExample() {
    val terminal = Terminal()
    val theme = terminal.theme
    val size = terminal.interactiveSelectList(
        listOf("Small", "Medium", "Large", "X-Large"),
        title = "Select a Pizza Size",
    )
    if (size == null) {
        terminal.danger("Aborted pizza order")
        return
    }
    val toppings = terminal.interactiveMultiSelectList {
        addEntry("Pepperoni", selected = true)
        addEntry("Sausage", selected = true)
        addEntry("Mushrooms")
        addEntry("Olives")
        addEntry("Pineapple")
        addEntry("Anchovies")
        title("Select Toppings")
        limit(4)
        filterable(true)
        keyLeft(KeyboardEvent("j"))
    }

    if (toppings == null) {
        terminal.danger("Aborted pizza order")
        return
    }
    val toppingString = if (toppings.isEmpty()) "no toppings" else toppings.joinToString()
    terminal.success("You ordered a ${theme.info(size)} pizza with ${theme.info(toppingString)}")
}

fun vimKeybindingsDemo() {
    val terminal = Terminal()

    terminal.println("Vim Keybindings Demo")
    terminal.println("Navigation keys available:")
    terminal.println("  • j/k or ↑/↓ - Move up/down")
    terminal.println("  • h/l or ←/→ - Page up/down")
    terminal.println("  • g/G or Home/End - Jump to first/last")
    terminal.println()

    val colors = listOf("Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Brown", "Black", "White", "Gray", "Cyan", "Magenta", "Lime", "Indigo")

    val selected = terminal.interactiveSelectList {
        entries(colors)
        title("Select your favorite color:")
    }

    if (selected != null) {
        terminal.success("You selected: $selected")
    } else {
        terminal.danger("Selection cancelled")
    }
}

fun multiSelectLanguagesDemo() {
    val terminal = Terminal()

    terminal.println("Multi-Select Demo with Vim Keybindings")
    terminal.println("Navigation: j/k (or arrows), h/l (page), g/G (first/last)")
    terminal.println("Selection: x or Space to toggle, Enter to confirm")
    terminal.println()

    val languages = listOf(
        "Kotlin", "Java", "Python", "JavaScript", "TypeScript",
        "Go", "Rust", "C++", "C#", "Swift", "Ruby", "PHP",
        "Scala", "Haskell", "Erlang", "Elixir", "Clojure"
    )

    val selected = terminal.interactiveMultiSelectList {
        entries(languages)
        title("Select your favorite programming languages:")
        limit(5)
        filterable(true)
        cursorMarker("👉")
        selectedMarker("👌")
        unselectedMarker("❓")
    }

    if (selected != null) {
        if (selected.isEmpty()) {
            terminal.println("No languages selected")
        } else {
            terminal.success("You selected: ${selected.joinToString(", ")}")
        }
    } else {
        terminal.danger("Selection cancelled")
    }
}
