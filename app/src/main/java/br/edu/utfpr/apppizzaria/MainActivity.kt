package br.edu.utfpr.apppizzaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.utfpr.apppizzaria.ui.PizzaApp
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPizzariaTheme(darkTheme = false) {
                PizzaApp()
            }
        }
    }
}