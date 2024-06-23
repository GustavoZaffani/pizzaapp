package br.edu.utfpr.apppizzaria.data.ingredient

enum class MeasurementUnit(val description: String) {
    KG("Quilograma"),
    UN("Unidade"),
    LT("Litro");

    companion object {
        fun fromDescription(description: String): MeasurementUnit {
            return values().find { it.description == description }!!
        }
    }
}
