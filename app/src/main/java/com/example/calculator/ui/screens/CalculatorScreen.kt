package com.example.calculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.logic.CalculatorLogic

@Composable
fun CalculatorScreen() {
    val calculatorLogic = remember { CalculatorLogic() }
    var display by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {
        calculatorLogic.displayFlow.collect { newValue ->
            display = newValue
        }
    }

    LaunchedEffect(Unit) {
        calculatorLogic.resultFlow.collect { newValue ->
            result = newValue
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = display,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcButton("C", modifier = Modifier.weight(1f), isSpecial = true) {
                    calculatorLogic.clear()
                }
                CalcButton("(", modifier = Modifier.weight(1f)) {
                    calculatorLogic.appendNumber("(")
                }
                CalcButton(")", modifier = Modifier.weight(1f)) {
                    calculatorLogic.appendNumber(")")
                }
                CalcButton("÷", modifier = Modifier.weight(1f)) {
                    calculatorLogic.appendOperator("/")
                }
                CalcButton("Del", modifier = Modifier.weight(1f), isSpecial = true) {
                    calculatorLogic.delete()
                }
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcButton("7", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("7") }
                CalcButton("8", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("8") }
                CalcButton("9", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("9") }
                CalcButton("×", modifier = Modifier.weight(1f)) { calculatorLogic.appendOperator("*") }
                CalcButton("√", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("sqrt(") }
            }

            // Row 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcButton("4", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("4") }
                CalcButton("5", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("5") }
                CalcButton("6", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("6") }
                CalcButton("-", modifier = Modifier.weight(1f)) { calculatorLogic.appendOperator("-") }
                CalcButton("%", modifier = Modifier.weight(1f)) { calculatorLogic.appendOperator("%") }
            }

            // Row 4
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcButton("1", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("1") }
                CalcButton("2", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("2") }
                CalcButton("3", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("3") }
                CalcButton("+", modifier = Modifier.weight(1f)) { calculatorLogic.appendOperator("+") }
                CalcButton("x²", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber("^2") }
            }

            // Row 5
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcButton("0", modifier = Modifier.weight(2f)) { calculatorLogic.appendNumber("0") }
                CalcButton(".", modifier = Modifier.weight(1f)) { calculatorLogic.appendNumber(".") }
                CalcButton("=", modifier = Modifier.weight(2f), isEqual = true) {
                    calculatorLogic.calculate()
                }
            }
        }
    }
}

@Composable
fun CalcButton(
    text: String,
    modifier: Modifier = Modifier,
    isSpecial: Boolean = false,
    isEqual: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isEqual -> MaterialTheme.colorScheme.primary
        isSpecial -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.surface
    }

    val contentColor = when {
        isEqual || isSpecial -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
