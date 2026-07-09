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
import com.example.calculator.logic.UnitConverter

enum class UnitCategory(val displayName: String) {
    LENGTH("Length"),
    WEIGHT("Weight"),
    TEMPERATURE("Temperature"),
    VOLUME("Volume"),
    AREA("Area"),
    TIME("Time")
}

@Composable
fun UnitConverterScreen() {
    var selectedCategory by remember { mutableStateOf(UnitCategory.LENGTH) }
    var inputValue by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("m") }
    var toUnit by remember { mutableStateOf("km") }
    var result by remember { mutableStateOf("") }

    val unitConverter = remember { UnitConverter() }

    fun updateResult() {
        if (inputValue.isNotBlank()) {
            result = try {
                unitConverter.convert(
                    selectedCategory,
                    inputValue.toDouble(),
                    fromUnit,
                    toUnit
                ).toString()
            } catch (e: Exception) {
                "Error"
            }
        } else {
            result = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Category Selector
        Text(
            "Select Category",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            UnitCategory.values().forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = category
                        fromUnit = unitConverter.getUnits(category).first()
                        toUnit = unitConverter.getUnits(category).last()
                        updateResult()
                    },
                    label = { Text(category.displayName) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Value
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                updateResult()
            },
            label = { Text("Enter Value") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        // From Unit
        Text(
            "From",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        UnitDropdown(
            units = unitConverter.getUnits(selectedCategory),
            selectedUnit = fromUnit,
            onUnitSelected = {
                fromUnit = it
                updateResult()
            }
        )

        // Swap Button
        Button(
            onClick = {
                val temp = fromUnit
                fromUnit = toUnit
                toUnit = temp
                updateResult()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.SwapVert, contentDescription = "Swap Units")
        }

        // To Unit
        Text(
            "To",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        UnitDropdown(
            units = unitConverter.getUnits(selectedCategory),
            selectedUnit = toUnit,
            onUnitSelected = {
                toUnit = it
                updateResult()
            }
        )

        // Result
        if (result.isNotEmpty()) {
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
                        "Result",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        result,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "$inputValue $fromUnit = $result $toUnit",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun UnitDropdown(
    units: List<String>,
    selectedUnit: String,
    onUnitSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedUnit,
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
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
