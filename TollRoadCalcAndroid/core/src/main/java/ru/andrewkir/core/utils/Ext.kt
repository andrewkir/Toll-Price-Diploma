package ru.andrewkir.core.utils

import androidx.compose.ui.Modifier

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}