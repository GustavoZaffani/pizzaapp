package br.edu.utfpr.apppizzaria.ui.shared.utils

import br.edu.utfpr.apppizzaria.ui.ingredient.form.FormField

class FormFieldUtils {

    companion object {
        fun isValid(fields: List<FormField>): Boolean {
            return fields.none { field -> field.errorMessageCode != null }
        }
    }
}