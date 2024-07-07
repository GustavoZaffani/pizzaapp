package br.edu.utfpr.apppizzaria.ui.shared.utils

import androidx.annotation.StringRes
import br.edu.utfpr.apppizzaria.R
import java.math.BigDecimal

data class FormField(
    val value: String = "",
    @StringRes
    val errorMessageCode: Int? = null
)

class FormFieldUtils {

    companion object {
        fun isValid(fields: List<FormField>): Boolean {
            return fields.none { field -> field.errorMessageCode != null }
        }

        fun validateFieldRequired(value: String): Int? = if (value.isBlank()) {
            R.string.field_required
        } else {
            null
        }

        fun validateLongPositive(value: String): Int? = if (value.toLong() <= 0) {
            R.string.field_must_be_greater_than_zero
        } else {
            null
        }

        fun validateBigDecimalPositive(value: String): Int? = if (value.toBigDecimal() <= BigDecimal.ZERO) {
            R.string.field_must_be_greater_than_zero
        } else {
            null
        }
    }
}