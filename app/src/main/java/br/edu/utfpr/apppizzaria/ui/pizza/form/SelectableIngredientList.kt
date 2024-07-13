package br.edu.utfpr.apppizzaria.ui.pizza.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.components.CardList
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import java.math.BigDecimal
import java.util.UUID

@Composable
fun SelectableIngredientList(
    modifier: Modifier = Modifier,
    ingredients: List<PizzaIngredientState>,
    onAddIngredient: (PizzaIngredientState) -> Unit,
    onRemoveIngredient: (PizzaIngredientState) -> Unit,
) {
    CardList(
        modifier = modifier.padding(bottom = 36.dp),
        items = ingredients,
        onItemPressed = {}
    ) { ingredient ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = ingredient.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ButtonChangeQuantity(
                            icon = Icons.Outlined.Remove,
                            descriptionIcon = stringResource(R.string.generic_to_remove),
                            onClick = { onRemoveIngredient(ingredient) }
                        )
                        Text(text = "${ingredient.quantity} ${ingredient.measurementUnit}")
                        ButtonChangeQuantity(
                            icon = Icons.Outlined.Add,
                            descriptionIcon = stringResource(R.string.generic_to_add),
                            onClick = { onAddIngredient(ingredient) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonChangeQuantity(
    icon: ImageVector,
    descriptionIcon: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Box(modifier = Modifier.clip(shape = CircleShape)) {
            Icon(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
                imageVector = icon,
                contentDescription = descriptionIcon,
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectableIngredientListPreview() {
    AppPizzariaTheme {
        SelectableIngredientList(
            onAddIngredient = {},
            onRemoveIngredient = {},
            ingredients = listOf(
                PizzaIngredientState(
                    ingredientId = UUID.randomUUID(),
                    name = "Alho",
                    measurementUnit = MeasurementUnit.UN,
                    quantity = BigDecimal.ONE
                ),
                PizzaIngredientState(
                    ingredientId = UUID.randomUUID(),
                    name = "Carne moída",
                    measurementUnit = MeasurementUnit.KG,
                    quantity = BigDecimal.ZERO
                )
            )

        )
    }
}