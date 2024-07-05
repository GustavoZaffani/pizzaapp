package br.edu.utfpr.apppizzaria.extensions

fun String.toFormattedPhone(): String = this.mapIndexed { index, char ->
    when {
        index == 0 -> "($char"
        index == 2 -> ") $char"
        (index == 6 && this.length < 11) ||
                (index == 7 && this.length == 11) -> "-$char"

        else -> char
    }
}.joinToString("")