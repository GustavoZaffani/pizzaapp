package br.edu.utfpr.apppizzaria.ui.ingredient.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.apppizzaria.data.ingredient.MeasurementUnit
import br.edu.utfpr.apppizzaria.ui.shared.components.AppBar
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
    LaunchedEffect(viewModel.uiState.ingredientSaved) {
        if (viewModel.uiState.ingredientSaved) {
            onIngredientSaved()
        }

    }

    LaunchedEffect(snackbarHostState, viewModel.uiState.hasErrorSaving) {
        if (viewModel.uiState.hasErrorSaving) {
            snackbarHostState.showSnackbar(
                "Não foi possível salvar o ingrediente. Aguarde um momento e tente novamente."
            )
        }
    }

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
        } else {
            FormContent(
                modifier = Modifier.padding(innerPadding),
                formState = viewModel.uiState.formState,
                allFormDisable = viewModel.uiState.isSaving,
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
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(all = 16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                IconButton(onClick = onSavePressed) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        tint = Color.White,
                        contentDescription = "Salvar"
                    )
                }
            }
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
            enabled = !allFormDisable
        )
        NumberField(
            label = "Quantidade",
            value = formState.quantity.value,
            onValueChange = onQuantityChanged,
            errorMessageCode = formState.quantity.errorMessageCode,
            onClearValue = onClearValueQuantity,
            enabled = !allFormDisable
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppPizzariaTheme {
        FormContent(
            formState = FormState(),
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