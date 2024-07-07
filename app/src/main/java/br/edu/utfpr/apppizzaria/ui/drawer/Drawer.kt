package br.edu.utfpr.apppizzaria.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.LocalPizza
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Segment
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.ui.Routes
import br.edu.utfpr.apppizzaria.ui.splash.SplashViewModel
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    currentRoute: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: DrawerViewModel = viewModel(factory = DrawerViewModel.Factory),
    onPizzaPressed: () -> Unit,
    onIngredientPressed: () -> Unit,
    onSalePressed: () -> Unit,
    onLogoutSuccess: () -> Unit,
    content: @Composable () -> Unit
) {
    LaunchedEffect(viewModel.uiState.logoutSuccess) {
        if (viewModel.uiState.logoutSuccess) {
            onLogoutSuccess()
        }
    }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val drawerWidth = maxWidth * 0.7f
                DrawerSheet(
                    modifier = Modifier.width(drawerWidth),
                    userLogged = viewModel.uiState.userLogged,
                    currentRoute = currentRoute,
                    onPizzaPressed = onPizzaPressed,
                    onIngredientPressed = onIngredientPressed,
                    onSalePressed = onSalePressed,
                    closeDrawer = { coroutineScope.launch { drawerState.close() } },
                    onLogout = viewModel::logout
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
    userLogged: String,
    onPizzaPressed: () -> Unit,
    onIngredientPressed: () -> Unit,
    onSalePressed: () -> Unit,
    closeDrawer: () -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = modifier.fillMaxSize()) {
            HeaderDrawer(
                user = userLogged,
                onLogout = {
                    closeDrawer()
                    onLogout()
                }
            )

            DrawerItemGroupTitle(title = "Cadastros")
            Divider(modifier = Modifier.padding(bottom = 8.dp))

            DrawerItem(
                imageVector = Icons.Outlined.Segment,
                label = "Ingredientes",
                isSelected = currentRoute == Routes.INGREDIENTS_LIST,
                onClick = {
                    closeDrawer()
                    onIngredientPressed()
                }
            )
            DrawerItem(
                imageVector = Icons.Outlined.LocalPizza,
                label = "Pizzas",
                isSelected = currentRoute == Routes.PIZZA_LIST,
                onClick = {
                    closeDrawer()
                    onPizzaPressed()
                }
            )

            DrawerItemGroupTitle(title = "Análise")
            Divider(modifier = Modifier.padding(bottom = 8.dp))

            DrawerItem(
                imageVector = Icons.Outlined.ShoppingCart,
                label = "Vendas",
                isSelected = currentRoute == Routes.SALES_LIST,
                onClick = {
                    closeDrawer()
                    onSalePressed()
                }
            )
//            DrawerItem(
//                imageVector = Icons.Outlined.Assessment,
//                label = "Relatórios",
//                isSelected = currentRoute == Routes.REPORTS_LIST,
//                onClick = {
//                    // TODO
//                    closeDrawer()
//                }
//            )
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
            userLogged = "CR7",
            onIngredientPressed = {},
            onPizzaPressed = {},
            onSalePressed = {},
            closeDrawer = {},
            onLogout = {}
        )
    }
}

@Composable
fun HeaderDrawer(
    modifier: Modifier = Modifier,
    user: String,
    onLogout: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Sair",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp)
                )
            }

            Column {
                Text(
                    text = "Bem-vindo, $user!",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeaderDrawerPreview() {
    AppPizzariaTheme {
        HeaderDrawer(
            user = "Pizzaria Toscana",
            onLogout = {}
        )
    }
}