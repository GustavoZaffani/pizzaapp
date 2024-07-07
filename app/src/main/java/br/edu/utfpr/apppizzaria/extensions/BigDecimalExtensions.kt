package br.edu.utfpr.apppizzaria.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formatToCurrency(): String {
    val format: NumberFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}