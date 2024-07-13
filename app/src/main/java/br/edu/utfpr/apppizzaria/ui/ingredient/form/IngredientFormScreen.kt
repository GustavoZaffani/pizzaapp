package br.edu.utfpr.apppizzaria.ui.ingredient.form

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
import br.edu.utfpr.apppizzaria.ui.shared.components.DefaultActionFormToolbar
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDefault
import br.edu.utfpr.apppizzaria.ui.shared.components.ErrorDetails
import br.edu.utfpr.apppizzaria.ui.shared.components.Loading
import br.edu.utfpr.apppizzaria.ui.shared.components.form.CurrencyField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.DropdownField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.NumberField
import br.edu.utfpr.apppizzaria.ui.shared.components.form.TextField
import br.edu.utfpr.apppizzaria.ui.theme.AppPizzariaTheme

@Composable
fun IngredientFormScreen(
    modifier: Modifier = Modifier,
    viewModel: IngredientFormViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackPressed: () -> Unit,
    onIngredientSaved: () -> Unit
) {
    val context = LocalContext.current
    var showHttpErrorModal by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiState.ingredientSaved) {
        if (viewModel.uiState.ingredientSaved) {
            Toast.makeText(
                context,
                "Ingrediente registrado com sucesso.",
                Toast.LENGTH_LONG
            ).show()
            onIngredientSaved()
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
            IngredientAppBar(
                isNewIngredient = viewModel.uiState.isNewIngredient,
                showActions = viewModel.uiState.isSuccessLoading,
                isSaving = viewModel.uiState.isSaving,
                onBackPressed = onBackPressed,
                onSavePressed = viewModel::save
            )
        }
    ) { innerPadding ->
        if (viewModel.uiState.isLoading) {
            Loading(text = "Carregando ingrediente...")
        } else if (viewModel.uiState.hasErrorLoading) {
            ErrorDefault(
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::loadIngredient,
                text = "Erro ao carregar o ingrediente"
            )
        } else {
            FormContent(
                modifier = Modifier.padding(innerPadding),
                formState = viewModel.uiState.formState,
                allFormDisable = viewModel.uiState.isSaving,
                isEditing = !viewModel.uiState.isNewIngredient,
                onNameChanged = viewModel::onNameChanged,
                onDescriptionChanged = viewModel::onDescriptionChanged,
                onPriceChanged = viewModel::onPriceChanged,
                onMeasureUnitChanged = viewModel::onMeasurementUnitChanged,
                onQuantityChanged = viewModel::onQuantityChanged,
                onClearValueName = viewModel::onClearValueName,
                onClearValueDescription = viewModel::onClearValueDescription,
                onClearValueQuantity = viewModel::onClearValueQuantity,
                onClearValuePrice = viewModel::onClearValuePrice,
            )
        }
    }
}

@Composable
private fun IngredientAppBar(
    modifier: Modifier = Modifier,
    isNewIngredient: Boolean,
    showActions: Boolean = false,
    isSaving: Boolean = false,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit
) {
    val title = if (isNewIngredient) "Novo ingrediente" else "Editar ingrediente"

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
private fun IngredientAppBarPreview() {
    AppPizzariaTheme {
        IngredientAppBar(
            isNewIngredient = true,
            onBackPressed = {},
            onSavePressed = {}
        )
    }
}

@Composable
private fun FormContent(
    modifier: Modifier = Modifier,
    formState: FormState,
    isEditing: Boolean,
    allFormDisable: Boolean = false,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onMeasureUnitChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onClearValueName: () -> Unit,
    onClearValueDescription: () -> Unit,
    onClearValueQuantity: () -> Unit,
    onClearValuePrice: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            label = "Nome",
            value = formState.name.value,
            onValueChange = onNameChanged,
            errorMessageCode = formState.name.errorMessageCode,
            onClearValue = onClearValueName,
            enabled = !allFormDisable
        )
        TextField(
            label = "Descrição",
            value = formState.description.value,
            onValueChange = onDescriptionChanged,
            errorMessageCode = formState.description.errorMessageCode,
            onClearValue = onClearValueDescription,
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
        DropdownField(
            label = "Unidade de medida",
            selectedValue = formState.measurementUnit.value,
            errorMessageCode = formState.measurementUnit.errorMessageCode,
            onValueChangedEvent = onMeasureUnitChanged,
            options = listOf(
                MeasurementUnit.UN.description,
                MeasurementUnit.LT.description,
                MeasurementUnit.KG.description
            ),
            enabled = !allFormDisable && !isEditing
        )
        NumberField(
            label = "Quantidade",
            value = formState.quantity.value,
            onValueChange = onQuantityChanged,
            errorMessageCode = formState.quantity.errorMessageCode,
            onClearValue = onClearValueQuantity,
            enabled = !allFormDisable && !isEditing
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppPizzariaTheme {
        FormContent(
            formState = FormState(),
            isEditing = false,
            onNameChanged = {},
            onDescriptionChanged = {},
            onPriceChanged = {},
            onQuantityChanged = {},
            onMeasureUnitChanged = {},
            onClearValueName = {},
            onClearValueDescription = {},
            onClearValuePrice = {},
            onClearValueQuantity = {}
        )
    }
}