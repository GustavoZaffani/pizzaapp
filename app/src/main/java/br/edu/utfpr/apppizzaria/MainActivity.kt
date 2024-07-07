package br.edu.utfpr.apppizzaria

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.utfpr.apppizzaria.ui.PizzaApp
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        setContent {
            AppPizzariaTheme(darkTheme = false) {
                PizzaApp()
            }
        }
    }

    companion object {
        lateinit  var appContext: Context
    }
}