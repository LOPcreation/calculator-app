package com.example.calculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.logic.CurrencyConverter
import kotlinx.coroutines.launch

@Composable
fun CurrencyConverterScreen() {
    val currencyConverter = remember { CurrencyConverter() }
    var inputValue by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("EUR") }
    var result by remember { mutableStateOf("") }
    var lastUpdated by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    fun convertCurrency() {
        if (inputValue.isNotBlank()) {
            scope.launch {
                isLoading = true
                errorMessage = ""
                try {
                    val conversionResult = currencyConverter.convert(
                        inputValue.toDouble(),
                        fromCurrency,
                        toCurrency
                    )
                    result = conversionResult.convertedAmount.toString()
                    lastUpdated = "Rate: ${conversionResult.exchangeRate} (${conversionResult.timestamp})"
                } catch (e: Exception) {
                    errorMessage = "Error: ${e.message ?: "Unknown error"}"
                    result = ""
                } finally {
                    isLoading = false
                }
            }
        }
    }

    LaunchedEffect(fromCurrency, toCurrency) {
        convertCurrency()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Input Value
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertCurrency()
            },
            label = { Text("Enter Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        // From Currency
        Text(
            "From",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        CurrencyDropdown(
            currencies = currencyConverter.supportedCurrencies,
            selectedCurrency = fromCurrency,
            onCurrencySelected = { fromCurrency = it }
        )

        // Swap Button
        Button(
            onClick = {
                val temp = fromCurrency
                fromCurrency = toCurrency
                toCurrency = temp
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.SwapVert, contentDescription = "Swap Currencies")
        }

        // To Currency
        Text(
            "To",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        CurrencyDropdown(
            currencies = currencyConverter.supportedCurrencies,
            selectedCurrency = toCurrency,
            onCurrencySelected = { toCurrency = it }
        )

        // Loading
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Error Message
        if (errorMessage.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    errorMessage,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }

        // Result
        if (result.isNotEmpty() && !isLoading) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Converted Amount",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        "$result $toCurrency",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (lastUpdated.isNotEmpty()) {
                        Text(
                            lastUpdated,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Text(
                        "$inputValue $fromCurrency = $result $toCurrency",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencyDropdown(
    currencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCurrency,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}
