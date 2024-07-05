package br.edu.utfpr.apppizzaria.ui.shared.utils

import androidx.annotation.StringRes

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
    }
}