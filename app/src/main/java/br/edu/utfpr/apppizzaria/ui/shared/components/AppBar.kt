package br.edu.utfpr.apppizzaria.ui.shared.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    showActions: Boolean,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            titleContentColor = Color.White
        ),
        title = {
            Text(text = title)
        },
        navigationIcon = navigationIcon,
        actions = {
            if (showActions) {
                actions()
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun IngredientAppBarPreview() {
    AppPizzariaTheme {
        AppBar(
            title = "Ingredientes",
            showActions = true
        )
    }
}