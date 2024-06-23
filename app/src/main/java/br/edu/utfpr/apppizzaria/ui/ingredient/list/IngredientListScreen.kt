package br.edu.utfpr.apppizzaria.ui.ingredient.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.data.ingredient.response.IngredientDefaultResponse
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
import br.edu.utfpr.apppizzaria.ui.shared.components.CardList
import br.edu.utfpr.apppizzaria.ui.shared.components.EmptyList
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import java.math.BigDecimal
import java.util.UUID

@Composable
fun IngredientListScreen(
    modifier: Modifier = Modifier,
    onNewIngredientPressed: () -> Unit,
    viewModel: IngredientsListViewModel = viewModel(),
    openDrawer: () -> Unit,
    onIngredientPressed: (IngredientDefaultResponse) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            IngredientAppBar(
                showActions = viewModel.uiState.isSuccess,
                onRefreshPressed = viewModel::loadIngredients,
                openDrawer = openDrawer
            )
        },
        floatingActionButton = {
            if (viewModel.uiState.isSuccess) {
                FloatingActionButton(onClick = onNewIngredientPressed) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Novo ingrediente"
                    )
                }
            }
        }
    ) { innerPadding ->
        if (viewModel.uiState.loading) {
            Loading(
                modifier = Modifier.padding(innerPadding),
                text = "Carregando ingredientes...",
            )
        } else if (viewModel.uiState.hasError) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadIngredients,
                text = "Erro ao carregar os ingredientes"
            )
        } else {
            IngredientList(
                modifier = Modifier.padding(innerPadding),
                ingredients = viewModel.uiState.ingredients,
                onItemPressed = onIngredientPressed
            )
        }
    }
}

@Composable
private fun IngredientList(
    modifier: Modifier = Modifier,
    ingredients: List<IngredientDefaultResponse> = listOf(),
    onItemPressed: (IngredientDefaultResponse) -> Unit
) {
    if (ingredients.isEmpty()) {
        EmptyList(
            modifier = modifier,
            description = "teste"
        )
    } else {
        IngredientListContent(
            modifier = modifier,
            ingredients = ingredients,
            onItemPressed = onItemPressed
        )
    }
}

@Composable
private fun IngredientListContent(
    modifier: Modifier = Modifier,
    ingredients: List<IngredientDefaultResponse>,
    onItemPressed: (IngredientDefaultResponse) -> Unit
) {
    CardList(
        modifier = modifier,
        items = ingredients,
        onItemPressed = onItemPressed
    ) { ingredient ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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

                val stockBackground = if (BigDecimal.ZERO >= ingredient.quantity) {
                    Color(0xC8BE0E0E)
                } else Color(0xC80D7901)

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(stockBackground)
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = ingredient.stockDescription, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = ingredient.price.toString(),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = ingredient.description,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun IngredientListContentPreview() {
    AppPizzariaTheme {
        IngredientListContent(
            ingredients = listOf(
                IngredientDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Alho",
                    description = "Poró",
                    measurementUnit = MeasurementUnit.UN,
                    price = BigDecimal.TEN,
                    quantity = BigDecimal.ONE
                ),
                IngredientDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Carne moída",
                    description = "Carne de primeira",
                    measurementUnit = MeasurementUnit.KG,
                    price = BigDecimal.valueOf(19.99),
                    quantity = BigDecimal.ZERO
                ),
                IngredientDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Alho",
                    description = "Poró",
                    measurementUnit = MeasurementUnit.UN,
                    price = BigDecimal.TEN,
                    quantity = BigDecimal.ONE
                ),
                IngredientDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Alho",
                    description = "Poró",
                    measurementUnit = MeasurementUnit.UN,
                    price = BigDecimal.TEN,
                    quantity = BigDecimal.ONE
                )
            ),
            onItemPressed = {}
        )
    }
}

@Composable
private fun IngredientAppBar(
    modifier: Modifier = Modifier,
    showActions: Boolean,
    onRefreshPressed: () -> Unit,
    openDrawer: () -> Unit
) {
    AppBar(
        modifier = modifier,
        title = "Ingredientes",
        showActions = showActions,
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    contentDescription = "Abrir menu"
                )
            }
        },
        actions = {
            IconButton(onClick = onRefreshPressed) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    tint = Color.White,
                    contentDescription = "Atualizar"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun IngredientAppBarPreview() {
    AppPizzariaTheme {
        IngredientAppBar(
            showActions = true,
            onRefreshPressed = {},
            openDrawer = {}
        )
    }
}