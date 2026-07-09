package com.example.calculator.logic

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionFactory

class CalculatorLogic {
    private var currentInput = ""
    private var currentResult = "0"

    private val _displayFlow = MutableStateFlow("0")
    val displayFlow = _displayFlow.asStateFlow()

    private val _resultFlow = MutableStateFlow("0")
    val resultFlow = _resultFlow.asStateFlow()

    fun appendNumber(number: String) {
        if (number == "(" || number == ")") {
            currentInput += number
        } else if (number == "sqrt(" || number == "^2") {
            currentInput += number
        } else if (number == ".") {
            if (!currentInput.contains(".")) {
                currentInput += number
            }
        } else {
            if (currentInput == "0" && number != ".") {
                currentInput = number
            } else {
                currentInput += number
            }
        }
        updateDisplay()
    }

    fun appendOperator(operator: String) {
        if (currentInput.isNotEmpty() && !currentInput.endsWith("+") &&
            !currentInput.endsWith("-") && !currentInput.endsWith("*") &&
            !currentInput.endsWith("/") && !currentInput.endsWith("%")
        ) {
            currentInput += operator
            updateDisplay()
        }
    }

    fun calculate() {
        try {
            val expression = currentInput
                .replace("×", "*")
                .replace("÷", "/")
                .replace("sqrt(", "sqrt(")
                .replace("^2", "^2")

            val expr = ExpressionFactory.create(expression)
            currentResult = expr.evaluate().toString()

            // Format result to avoid long decimals
            currentResult = try {
                val resultDouble = currentResult.toDouble()
                if (resultDouble == resultDouble.toLong().toDouble()) {
                    resultDouble.toLong().toString()
                } else {
                    String.format("%.6f", resultDouble).trimEnd('0').trimEnd('.')
                }
            } catch (e: Exception) {
                "Error"
            }

            currentInput = currentResult
        } catch (e: Exception) {
            currentResult = "Error"
        }
        updateDisplay()
    }

    fun clear() {
        currentInput = ""
        currentResult = "0"
        updateDisplay()
    }

    fun delete() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
        }
        if (currentInput.isEmpty()) {
            currentInput = "0"
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        _displayFlow.value = currentInput.ifEmpty { "0" }
        _resultFlow.value = currentResult
    }
}
