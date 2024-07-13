package br.edu.utfpr.apppizzaria.ui.sale.list

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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.data.sale.response.SaleDefaultResponse
import br.edu.utfpr.apppizzaria.data.sale.response.SaleItemDefaultResponse
import br.edu.utfpr.apppizzaria.extensions.formatToCurrency
import br.edu.utfpr.apppizzaria.extensions.toFormattedString
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
import br.edu.utfpr.apppizzaria.ui.shared.components.CardList
import br.edu.utfpr.apppizzaria.ui.shared.components.EmptyList
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import java.math.BigDecimal
import java.util.UUID

@Composable
fun SaleListScreen(
    modifier: Modifier = Modifier,
    viewModel: SaleListViewModel = viewModel(),
    openDrawer: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SaleAppBar(
                showActions = viewModel.uiState.isSuccess,
                onRefreshPressed = viewModel::loadSales,
                openDrawer = openDrawer
            )
        }
    ) { innerPadding ->
        if (viewModel.uiState.loading) {
            Loading(
                modifier = Modifier.padding(innerPadding),
                text = stringResource(R.string.sales_list_loading_sales),
            )
        } else if (viewModel.uiState.hasError) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadSales,
                text = stringResource(R.string.sales_list_loading_error)
            )
        } else {
            SalesList(
                modifier = Modifier.padding(innerPadding),
                sales = viewModel.uiState.sales
            )
        }
    }
}

@Composable
private fun SalesList(
    modifier: Modifier = Modifier,
    sales: List<SaleDefaultResponse> = listOf()
) {
    if (sales.isEmpty()) {
        EmptyList(
            modifier = modifier,
            description = stringResource(R.string.sales_list_empty_list)
        )
    } else {
        SaleListContent(
            modifier = modifier,
            sales = sales
        )
    }
}

@Composable
private fun SaleListContent(
    modifier: Modifier = Modifier,
    sales: List<SaleDefaultResponse>
) {
    CardList(
        modifier = modifier,
        items = sales,
        onItemPressed = {}
    ) { sale ->
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
                        text = sale.saleDate.toFormattedString(),
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
                    Text(text = sale.total.formatToCurrency(), color = Color.White)
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
                            text = stringResource(R.string.sales_list_pizzas),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            text = sale.pizzasList,
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
private fun SaleListContentPreview() {
    AppPizzariaTheme {
        SaleListContent(
            sales = listOf(
                SaleDefaultResponse(
                    id = UUID.randomUUID(),
                    saleItems = listOf(
                        SaleItemDefaultResponse(
                            pizzaName = "Calabresa",
                            quantity = 10
                        ),
                        SaleItemDefaultResponse(
                            pizzaName = "Quatro queijos",
                            quantity = 5
                        )
                    ),
                    total = BigDecimal.TEN
                ),
            )
        )
    }
}

@Composable
private fun SaleAppBar(
    modifier: Modifier = Modifier,
    showActions: Boolean,
    onRefreshPressed: () -> Unit,
    openDrawer: () -> Unit
) {
    AppBar(
        modifier = modifier,
        title = stringResource(R.string.sales_list_appbar_title),
        showActions = showActions,
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.generic_open_menu)
                )
            }
        },
        actions = {
            IconButton(onClick = onRefreshPressed) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.generic_to_update)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SaleAppBarPreview() {
    AppPizzariaTheme {
        SaleAppBar(
            showActions = true,
            onRefreshPressed = {},
            openDrawer = {}
        )
    }
}