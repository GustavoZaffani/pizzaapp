package br.edu.utfpr.apppizzaria.ui.pizza.form

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
import br.edu.utfpr.apppizzaria.ui.shared.components.DefaultActionFormToolbar
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDetails
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.shared.components.SectionHeader
import br.edu.utfpr.apppizzaria.ui.shared.components.form.CurrencyField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.TextField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme
import java.math.BigDecimal
import java.util.UUID

@Composable
fun PizzaFormScreen(
    modifier: Modifier = Modifier,
    viewModel: PizzaFormViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackPressed: () -> Unit,
    onPizzaSaved: () -> Unit
) {
    val context = LocalContext.current
    var showHttpErrorModal by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiState.pizzaSaved) {
        if (viewModel.uiState.pizzaSaved) {
            Toast.makeText(
                context,
                "Pizza registrada com sucesso.",
                Toast.LENGTH_LONG
            ).show()
            onPizzaSaved()
        }
    }

    LaunchedEffect(snackbarHostState, viewModel.uiState.hasUnexpectedError) {
        if (viewModel.uiState.hasUnexpectedError) {
            snackbarHostState.showSnackbar(
                "Não foi possível salvar o ingrediente. Aguarde um momento e tente novamente."
            )
        }
    }

    LaunchedEffect(viewModel.uiState.hasHttpError) {
        if (viewModel.uiState.hasHttpError) {
            showHttpErrorModal = true
        }
    }

    ErrorDetails(
        errorData = viewModel.uiState.errorBody,
        showModal = showHttpErrorModal,
        onDismissModal = { showHttpErrorModal = false }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            PizzaAppBar(
                isNewPizza = viewModel.uiState.isNewPizza,
                showActions = viewModel.uiState.isSuccessLoading,
                isSaving = viewModel.uiState.isSaving,
                onBackPressed = onBackPressed,
                onSavePressed = viewModel::save
            )
        }
    ) { innerPadding ->
        if (viewModel.uiState.hasAnyLoading) {
            val textLoading =
                if (viewModel.uiState.isLoadingPizza) "Carregando pizza..." else "Buscando ingredientes..."
            Loading(
                modifier = modifier.padding(innerPadding),
                text = textLoading
            )
        } else if (viewModel.uiState.hasErrorLoadingPizza) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadPizza,
                text = "Erro ao carregar a pizza"
            )
        } else if (viewModel.uiState.hasErrorLoadingIngredients) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadInfoToAddPizza,
                text = "Erro ao carregar os ingredientes da pizza"
            )
        } else {
            FormContent(
                modifier = Modifier.padding(innerPadding),
                isNewPizza = viewModel.uiState.isNewPizza,
                formState = viewModel.uiState.formState,
                allFormDisable = viewModel.uiState.isSaving,
                onNameChanged = viewModel::onNameChanged,
                onPriceChanged = viewModel::onPriceChanged,
                onClearValueName = viewModel::onClearValueName,
                onClearValuePrice = viewModel::onClearValuePrice,
                onAddIngredient = viewModel::onAddIngredient,
                onRemoveIngredient = viewModel::onRemoveIngredient
            )
        }
    }
}

@Composable
private fun PizzaAppBar(
    modifier: Modifier = Modifier,
    isNewPizza: Boolean,
    showActions: Boolean = false,
    isSaving: Boolean = false,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit
) {
    val title = if (isNewPizza) "Nova pizza" else "Editar pizza"

    AppBar(
        modifier = modifier,
        title = title,
        showActions = showActions,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Voltar"
                )
            }
        },
        actions = {
            DefaultActionFormToolbar(
                isSaving = isSaving,
                onSavePressed = onSavePressed
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PizzaAppBarPreview() {
    AppPizzariaTheme {
        PizzaAppBar(
            isNewPizza = true,
            onBackPressed = {},
            onSavePressed = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormContent(
    isNewPizza: Boolean,
    modifier: Modifier = Modifier,
    allFormDisable: Boolean = false,
    formState: FormState,
    onNameChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onClearValueName: () -> Unit,
    onClearValuePrice: () -> Unit,
    onAddIngredient: (PizzaIngredientState) -> Unit,
    onRemoveIngredient: (PizzaIngredientState) -> Unit
) {

    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader(
            text = "Dados gerais"
        )

        TextField(
            label = "Nome",
            value = formState.name.value,
            onValueChange = onNameChanged,
            errorMessageCode = formState.name.errorMessageCode,
            onClearValue = onClearValueName,
            enabled = !allFormDisable
        )
        CurrencyField(
            label = "Preço",
            value = formState.price.value,
            onValueChange = onPriceChanged,
            errorMessageCode = formState.price.errorMessageCode,
            onClearValue = onClearValuePrice,
            enabled = !allFormDisable
        )


        SectionHeader(
            text = "Ingredientes"
        )

        if (formState.ingredientsAdded.isEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Nenhum ingrediente adicionado",
                color = MaterialTheme.colorScheme.error,
                fontStyle = FontStyle.Italic
            )
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = formState.ingredientsAdded.joinToString("\n"),
                fontStyle = FontStyle.Italic
            )
        }

        if (isNewPizza && !allFormDisable) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { showModal = true }
            ) {
                Text(text = "Adicionar ingredientes")
            }
        }


        if (showModal) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxHeight(1f),
                onDismissRequest = { showModal = false },
                sheetState = rememberModalBottomSheetState()
            ) {
                SelectableIngredientList(
                    ingredients = formState.ingredients,
                    onAddIngredient = onAddIngredient,
                    onRemoveIngredient = onRemoveIngredient
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppPizzariaTheme {
        FormContent(
            isNewPizza = true,
            formState = FormState(
                ingredients = listOf(
                    PizzaIngredientState(
                        ingredientId = UUID.randomUUID(),
                        name = "Alho",
                        measurementUnit = MeasurementUnit.UN,
                        quantity = BigDecimal.TEN
                    ),
                    PizzaIngredientState(
                        ingredientId = UUID.randomUUID(),
                        name = "Cebola",
                        measurementUnit = MeasurementUnit.KG,
                        quantity = BigDecimal.ONE
                    )
                )
            ),
            onNameChanged = {},
            onPriceChanged = {},
            onClearValueName = {},
            onClearValuePrice = {},
            onAddIngredient = {},
            onRemoveIngredient = {}
        )
    }
}