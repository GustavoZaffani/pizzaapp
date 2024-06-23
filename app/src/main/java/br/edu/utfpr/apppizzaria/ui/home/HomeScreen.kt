package br.edu.utfpr.apppizzaria.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.apppizzaria.R
import br.edu.utfpr.apppizzaria.ui.shared.components.AnimationTypingText
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_local_pizza),
            contentDescription = "Logo",
            modifier = Modifier.size(128.dp)
        )
        AnimationTypingText(text = "Pizza's App", typingSpeed = 200L)
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun HomeScreenPreview() {
    AppPizzariaTheme {
        HomeScreen()
    }
}