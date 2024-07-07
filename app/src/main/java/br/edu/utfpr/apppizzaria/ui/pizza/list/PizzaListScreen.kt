package br.edu.utfpr.apppizzaria.ui.pizza.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.pizza.response.PizzaDefaultResponse
import br.edu.utfpr.apppizzaria.data.pizza.response.PizzaIngredientDefaultResponse
import br.edu.utfpr.apppizzaria.extensions.formatToCurrency
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
import br.edu.utfpr.apppizzaria.ui.shared.components.CardList
import br.edu.utfpr.apppizzaria.ui.shared.components.EmptyList
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import java.math.BigDecimal
import java.util.UUID

@Composable
fun PizzaListScreen(
    modifier: Modifier = Modifier,
    onNewPizzaPressed: () -> Unit,
    viewModel: PizzaListViewModel = viewModel(),
    openDrawer: () -> Unit,
    onPizzaPressed: (PizzaDefaultResponse) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            PizzaAppBar(
                showActions = viewModel.uiState.isSuccess,
                onRefreshPressed = viewModel::loadPizzas,
                openDrawer = openDrawer
            )
        },
        floatingActionButton = {
            if (viewModel.uiState.isSuccess) {
                FloatingActionButton(onClick = onNewPizzaPressed) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Nova pizza"
                    )
                }
            }
        }
    ) { innerPadding ->
        if (viewModel.uiState.loading) {
            Loading(
                modifier = Modifier.padding(innerPadding),
                text = "Carregando pizzas...",
            )
        } else if (viewModel.uiState.hasError) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadPizzas,
                text = "Erro ao carregar as pizzas"
            )
        } else {
            PizzaList(
                modifier = Modifier.padding(innerPadding),
                pizzas = viewModel.uiState.pizzas,
                onItemPressed = onPizzaPressed
            )
        }
    }
}

@Composable
private fun PizzaList(
    modifier: Modifier = Modifier,
    pizzas: List<PizzaDefaultResponse> = listOf(),
    onItemPressed: (PizzaDefaultResponse) -> Unit
) {
    if (pizzas.isEmpty()) {
        EmptyList(
            modifier = modifier,
            description = "teste"
        )
    } else {
        PizzaListContent(
            modifier = modifier,
            pizzas = pizzas,
            onItemPressed = onItemPressed
        )
    }
}

@Composable
private fun PizzaListContent(
    modifier: Modifier = Modifier,
    pizzas: List<PizzaDefaultResponse>,
    onItemPressed: (PizzaDefaultResponse) -> Unit
) {
    CardList(
        modifier = modifier,
        items = pizzas,
        onItemPressed = onItemPressed
    ) { pizza ->
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
                        text = pizza.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xC80D7901))
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = pizza.price.formatToCurrency(), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(
                            text = "Ingredientes: ",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic)
                        Text(
                            text = pizza.ingredientList,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun PizzaListContentPreview() {
    AppPizzariaTheme {
        PizzaListContent(
            pizzas = listOf(
                PizzaDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Alho",
                    price = BigDecimal.ONE,
                    ingredients = listOf(
                        PizzaIngredientDefaultResponse(
                            id = UUID.randomUUID(),
                            ingredientId = UUID.randomUUID(),
                            ingredientName = "Cebola",
                            quantity = BigDecimal.ONE
                        ),
                        PizzaIngredientDefaultResponse(
                            id = UUID.randomUUID(),
                            ingredientId = UUID.randomUUID(),
                            ingredientName = "Alho",
                            quantity = BigDecimal.ONE
                        )
                    )
                ),
                PizzaDefaultResponse(
                    id = UUID.randomUUID(),
                    name = "Alho",
                    price = BigDecimal.ONE,
                    ingredients = listOf(
                        PizzaIngredientDefaultResponse(
                            id = UUID.randomUUID(),
                            ingredientId = UUID.randomUUID(),
                            ingredientName = "Cebola",
                            quantity = BigDecimal.ONE
                        ),
                        PizzaIngredientDefaultResponse(
                            id = UUID.randomUUID(),
                            ingredientId = UUID.randomUUID(),
                            ingredientName = "Alho",
                            quantity = BigDecimal.ONE
                        )
                    )
                )
            ),
            onItemPressed = {}
        )
    }
}

@Composable
private fun PizzaAppBar(
    modifier: Modifier = Modifier,
    showActions: Boolean,
    onRefreshPressed: () -> Unit,
    openDrawer: () -> Unit
) {
    AppBar(
        modifier = modifier,
        title = "Pizzas",
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
fun PizzaAppBarPreview() {
    AppPizzariaTheme {
        PizzaAppBar(
            showActions = true,
            onRefreshPressed = {},
            openDrawer = {}
        )
    }
}