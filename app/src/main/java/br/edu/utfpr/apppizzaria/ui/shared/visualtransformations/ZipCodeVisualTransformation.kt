package br.edu.utfpr.apppizzaria.ui.shared.visualtransformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ZipCodeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.filter { it.isDigit() }
        val cepFormatted = when (trimmed.length) {
            in 0..5 -> trimmed
            in 6..8 -> "${trimmed.substring(0, 5)}-${trimmed.substring(5)}"
            else -> "${trimmed.substring(0, 5)}-${trimmed.substring(5, 8)}"
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 5 -> offset
                    offset <= 8 -> offset + 1
                    else -> 9
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 5 -> offset
                    offset <= 6 -> 5
                    offset <= 9 -> offset - 1
                    else -> 8
                }
            }
        }
        return TransformedText(AnnotatedString(cepFormatted), offsetMapping)
    }
}