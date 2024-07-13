package br.edu.utfpr.apppizzaria.ui.shared.utils

import androidx.annotation.StringRes
import br.edu.utfpr.apppizzaria.R

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
    }
}