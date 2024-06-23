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
import br.edu.utfpr.apppizzaria.ui.home.HomeScreen
import br.edu.utfpr.apppizzaria.ui.ingredient.form.IngredientFormScreen
import br.edu.utfpr.apppizzaria.ui.ingredient.list.IngredientListScreen
import br.edu.utfpr.apppizzaria.ui.shared.components.Drawer
import br.edu.utfpr.apppizzaria.ui.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Screens {
    const val INGREDIENTS_LIST = "ingredients"
    const val INGREDIENTS_FORM = "ingredientsForm"
    const val SPLASH = "splash"
    const val HOME = "home"
}

object Arguments {
    const val INGREDIENT_ID = "ingredientId"
}

object Routes {
    const val SPLASH = Screens.SPLASH
    const val HOME = Screens.HOME
    const val INGREDIENTS_LIST = Screens.INGREDIENTS_LIST
    const val INGREDIENTS_FORM =
        "${Screens.INGREDIENTS_FORM}?${Arguments.INGREDIENT_ID}={${Arguments.INGREDIENT_ID}}"
    const val PIZZAS_LIST = "pizza" // TODO
    const val SALES_LIST = "sales" // TODO
    const val REPORTS_LIST = "reports" // TODO
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
            SplashScreen(onFinishSplash = {
                navController.navigate(Routes.HOME) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Routes.HOME) {
            Drawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                onPizzaPressed = { },
                onIngredientPressed = { navigateToIngredientList(navController) }
            ) {
                HomeScreen()
            }
        }
        composable(route = Routes.INGREDIENTS_LIST) {
            Drawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                onPizzaPressed = { },
                onIngredientPressed = { navigateToIngredientList(navController) }
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
                    navigateToIngredientList(navController)
                }
            )
        }
    }
}

private fun navigateToIngredientList(navController: NavHostController) {
    navController.navigate(Routes.INGREDIENTS_LIST) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}