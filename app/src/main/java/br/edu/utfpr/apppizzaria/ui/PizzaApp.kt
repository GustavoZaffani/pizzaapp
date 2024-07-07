package br.edu.utfpr.apppizzaria.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.utfpr.apppizzaria.ui.drawer.Drawer
import br.edu.utfpr.apppizzaria.ui.home.HomeScreen
import br.edu.utfpr.apppizzaria.ui.ingredient.form.IngredientFormScreen
import br.edu.utfpr.apppizzaria.ui.ingredient.list.IngredientListScreen
import br.edu.utfpr.apppizzaria.ui.pizza.form.PizzaFormScreen
import br.edu.utfpr.apppizzaria.ui.pizza.list.PizzaListScreen
import br.edu.utfpr.apppizzaria.ui.sale.list.SaleListScreen
import br.edu.utfpr.apppizzaria.ui.splash.SplashScreen
import br.edu.utfpr.apppizzaria.ui.user.login.LoginScreen
import br.edu.utfpr.apppizzaria.ui.user.register.pizzeria.PizzeriaRegisterScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Screens {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LOGIN = "login"
    const val REGISTER_PIZZERIA = "registerPizzeria"
    const val INGREDIENTS_LIST = "ingredients"
    const val INGREDIENTS_FORM = "ingredientsForm"
    const val PIZZA_LIST = "pizza"
    const val PIZZA_FORM = "pizzaForm"
    const val SALE_LIST = "sales"
}

object Arguments {
    const val INGREDIENT_ID = "ingredientId"
    const val PIZZA_ID = "pizzaId"
}

object Routes {
    const val SPLASH = Screens.SPLASH
    const val HOME = Screens.HOME
    const val LOGIN = Screens.LOGIN
    const val REGISTER_PIZZERIA = Screens.REGISTER_PIZZERIA
    const val INGREDIENTS_LIST = Screens.INGREDIENTS_LIST
    const val INGREDIENTS_FORM =
        "${Screens.INGREDIENTS_FORM}?${Arguments.INGREDIENT_ID}={${Arguments.INGREDIENT_ID}}"
    const val PIZZA_LIST = Screens.PIZZA_LIST
    const val PIZZA_FORM =
        "${Screens.PIZZA_FORM}?${Arguments.PIZZA_ID}={${Arguments.PIZZA_ID}}"
    const val SALES_LIST = Screens.SALE_LIST
}

@Composable
fun PizzaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startRoute: String = Routes.SPLASH
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startRoute

    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        composable(route = Routes.SPLASH) {
            SplashScreen(onFinishSplash = { userLogged ->
                if (userLogged) navigateTo(navController, Routes.HOME)
                else navigateTo(navController, Routes.LOGIN)
            })
        }
        composable(route = Routes.LOGIN) {
            LoginScreen(
                onClickNewRegister = { navController.navigate(Routes.REGISTER_PIZZERIA) },
                onLoginSuccess = {
                    navigateTo(navController, Routes.HOME)
                }
            )
        }
        composable(route = Routes.REGISTER_PIZZERIA) {
            PizzeriaRegisterScreen(
                onRegisterSaved = {
                    navigateTo(navController, Routes.LOGIN)
                }
            )
        }
        composable(route = Routes.HOME) {
            DefaultDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navController = navController
            ) {
                HomeScreen()
            }
        }
        composable(route = Routes.INGREDIENTS_LIST) {
            DefaultDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navController = navController
            ) {
                IngredientListScreen(
                    onNewIngredientPressed = { navController.navigate(Screens.INGREDIENTS_FORM) },
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onIngredientPressed = { ingredient ->
                        navController.navigate("${Screens.INGREDIENTS_FORM}?${Arguments.INGREDIENT_ID}=${ingredient.id}")
                    }
                )
            }
        }
        composable(
            route = Routes.INGREDIENTS_FORM,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.StringType; nullable = true }
            )
        ) {
            IngredientFormScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onIngredientSaved = {
                    navigateTo(navController, Routes.INGREDIENTS_LIST)
                }
            )
        }
        composable(route = Routes.PIZZA_LIST) {
            DefaultDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navController = navController
            ) {
                PizzaListScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNewPizzaPressed = { navController.navigate(Screens.PIZZA_FORM) },
                    onPizzaPressed = { pizza ->
                        navController.navigate("${Screens.PIZZA_FORM}?${Arguments.PIZZA_ID}=${pizza.id}")
                    }
                )
            }
        }
        composable(
            route = Routes.PIZZA_FORM,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.StringType; nullable = true }
            )
        ) {
            PizzaFormScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onPizzaSaved = {
                    navigateTo(navController, Routes.PIZZA_LIST)
                }
            )
        }
        composable(route = Routes.SALES_LIST) {
            DefaultDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navController = navController
            ) {
                SaleListScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                )
            }
        }
    }
}

@Composable
fun DefaultDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    Drawer(
        drawerState = drawerState,
        currentRoute = currentRoute,
        onPizzaPressed = { navigateTo(navController, Routes.PIZZA_LIST) },
        onIngredientPressed = { navigateTo(navController, Routes.INGREDIENTS_LIST) },
        onSalePressed = { navigateTo(navController, Routes.SALES_LIST) },
        onLogoutSuccess = { navigateTo(navController, Routes.LOGIN) }
    ) {
        content()
    }
}

private fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}