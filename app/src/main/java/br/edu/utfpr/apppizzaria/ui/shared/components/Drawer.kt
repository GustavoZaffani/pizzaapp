package br.edu.utfpr.apppizzaria.ui.shared.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.Segment
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.apppizzaria.ui.Routes
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    currentRoute: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onPizzaPressed: () -> Unit,
    onIngredientPressed: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val drawerWidth = maxWidth * 0.7f
                DrawerSheet(
                    modifier = Modifier.width(drawerWidth),
                    currentRoute = currentRoute,
                    onPizzaPressed = onPizzaPressed,
                    onIngredientPressed = onIngredientPressed,
                    closeDrawer = { coroutineScope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        content()
    }
}

@Composable
private fun DrawerSheet(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onPizzaPressed: () -> Unit,
    onIngredientPressed: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = modifier.fillMaxSize()) {
            DrawerItemGroupTitle(title = "Cadastros")
            Divider(modifier = Modifier.padding(bottom = 8.dp))

            DrawerItem(
                imageVector = Icons.Outlined.Segment,
                label = "Ingredientes",
                isSelected = currentRoute == Routes.INGREDIENTS_LIST,
                onClick = {
                    onIngredientPressed()
                    closeDrawer()
                }
            )
            DrawerItem(
                imageVector = Icons.Outlined.LocalPizza,
                label = "Pizzas",
                isSelected = currentRoute == Routes.PIZZAS_LIST,
                onClick = {
                    onPizzaPressed()
                    closeDrawer()
                }
            )

            DrawerItemGroupTitle(title = "Análise")
            Divider(modifier = Modifier.padding(bottom = 8.dp))

            DrawerItem(
                imageVector = Icons.Outlined.ShoppingCart,
                label = "Vendas",
                isSelected = currentRoute == Routes.SALES_LIST,
                onClick = {
                    // TODO
                    closeDrawer()
                }
            )
            DrawerItem(
                imageVector = Icons.Outlined.Assessment,
                label = "Relatórios",
                isSelected = currentRoute == Routes.REPORTS_LIST,
                onClick = {
                    // TODO
                    closeDrawer()
                }
            )
        }
    }
}

@Composable
private fun DrawerItemGroupTitle(title: String) {
    Text(
        text = title.uppercase(),
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
private fun DrawerItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = modifier,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = label
            )
        },
        label = { Text(text = label) },
        selected = isSelected,
        onClick = onClick
    )
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun DrawerPreview() {
    AppPizzariaTheme {
        DrawerSheet(
            currentRoute = Routes.INGREDIENTS_LIST,
            onIngredientPressed = {},
            onPizzaPressed = {},
            closeDrawer = {}
        )
    }
}