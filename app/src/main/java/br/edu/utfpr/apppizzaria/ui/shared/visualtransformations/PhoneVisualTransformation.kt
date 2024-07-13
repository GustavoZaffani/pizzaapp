package br.edu.utfpr.apppizzaria.ui.shared.visualtransformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import br.edu.utfpr.apppizzaria.extensions.toFormattedPhone

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val phone = text.text
        val formattedPhone = phone.toFormattedPhone()

        return TransformedText(
            AnnotatedString(formattedPhone),
            TelefoneOffsetMapping
        )
    }

    object TelefoneOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 6 -> offset + 4
                offset > 2 -> offset + 3
                offset > 0 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 6 -> offset - 4
                offset > 2 -> offset - 3
                offset > 0 -> offset - 1
                else -> offset
            }
        }
    }
}