package com.example.calculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.screens.CalculatorScreen
import com.example.calculator.ui.screens.CurrencyConverterScreen
import com.example.calculator.ui.screens.UnitConverterScreen

enum class AppTab(val label: String) {
    CALCULATOR("Calculator"),
    UNIT_CONVERTER("Unit Converter"),
    CURRENCY_CONVERTER("Currency Converter")
}

@Composable
fun CalculatorApp() {
    var selectedTab by remember { mutableStateOf(AppTab.CALCULATOR) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.label, maxLines = 1) }
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            when (selectedTab) {
                AppTab.CALCULATOR -> CalculatorScreen()
                AppTab.UNIT_CONVERTER -> UnitConverterScreen()
                AppTab.CURRENCY_CONVERTER -> CurrencyConverterScreen()
            }
        }
    }
}
