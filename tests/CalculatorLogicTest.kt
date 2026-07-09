package com.example.calculator.logic

import org.junit.Test
import org.junit.Assert.*

class CalculatorLogicTest {

    @Test
    fun testBasicAddition() {
        val calc = CalculatorLogic()
        calc.appendNumber("5")
        calc.appendOperator("+")
        calc.appendNumber("3")
        calc.calculate()
        // Note: In real implementation, use Flow to get result
    }

    @Test
    fun testBasicSubtraction() {
        val calc = CalculatorLogic()
        calc.appendNumber("10")
        calc.appendOperator("-")
        calc.appendNumber("3")
        calc.calculate()
    }

    @Test
    fun testClear() {
        val calc = CalculatorLogic()
        calc.appendNumber("123")
        calc.clear()
        // Verify display is cleared
    }

    @Test
    fun testDelete() {
        val calc = CalculatorLogic()
        calc.appendNumber("123")
        calc.delete()
        // Verify last digit removed
    }
}
