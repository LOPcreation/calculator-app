package com.example.calculator.logic

import com.example.calculator.ui.screens.UnitCategory
import kotlin.math.sqrt

class UnitConverter {
    private val lengthUnits = mapOf(
        "mm" to 0.001,
        "cm" to 0.01,
        "m" to 1.0,
        "km" to 1000.0,
        "in" to 0.0254,
        "ft" to 0.3048,
        "yd" to 0.9144,
        "mi" to 1609.34
    )

    private val weightUnits = mapOf(
        "mg" to 0.001,
        "g" to 1.0,
        "kg" to 1000.0,
        "oz" to 28.3495,
        "lb" to 453.592,
        "ton" to 1000000.0
    )

    private val volumeUnits = mapOf(
        "ml" to 1.0,
        "l" to 1000.0,
        "gal" to 3785.41,
        "pt" to 473.176,
        "fl oz" to 29.5735
    )

    private val areaUnits = mapOf(
        "mm²" to 0.000001,
        "cm²" to 0.0001,
        "m²" to 1.0,
        "km²" to 1000000.0,
        "in²" to 0.00064516,
        "ft²" to 0.092903,
        "yd²" to 0.836127,
        "mi²" to 2589988.0
    )

    private val timeUnits = mapOf(
        "ms" to 0.001,
        "s" to 1.0,
        "min" to 60.0,
        "hr" to 3600.0,
        "day" to 86400.0
    )

    fun getUnits(category: UnitCategory): List<String> {
        return when (category) {
            UnitCategory.LENGTH -> lengthUnits.keys.toList()
            UnitCategory.WEIGHT -> weightUnits.keys.toList()
            UnitCategory.TEMPERATURE -> listOf("°C", "°F", "K")
            UnitCategory.VOLUME -> volumeUnits.keys.toList()
            UnitCategory.AREA -> areaUnits.keys.toList()
            UnitCategory.TIME -> timeUnits.keys.toList()
        }
    }

    fun convert(category: UnitCategory, value: Double, fromUnit: String, toUnit: String): Double {
        return when (category) {
            UnitCategory.LENGTH -> convertLinear(value, fromUnit, toUnit, lengthUnits)
            UnitCategory.WEIGHT -> convertLinear(value, fromUnit, toUnit, weightUnits)
            UnitCategory.TEMPERATURE -> convertTemperature(value, fromUnit, toUnit)
            UnitCategory.VOLUME -> convertLinear(value, fromUnit, toUnit, volumeUnits)
            UnitCategory.AREA -> convertLinear(value, fromUnit, toUnit, areaUnits)
            UnitCategory.TIME -> convertLinear(value, fromUnit, toUnit, timeUnits)
        }
    }

    private fun convertLinear(value: Double, fromUnit: String, toUnit: String, conversionMap: Map<String, Double>): Double {
        val valueInBase = value * (conversionMap[fromUnit] ?: 1.0)
        return valueInBase / (conversionMap[toUnit] ?: 1.0)
    }

    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        // Convert to Celsius first
        val celsius = when (fromUnit) {
            "°C" -> value
            "°F" -> (value - 32) * 5 / 9
            "K" -> value - 273.15
            else -> value
        }

        // Convert from Celsius to target
        return when (toUnit) {
            "°C" -> celsius
            "°F" -> celsius * 9 / 5 + 32
            "K" -> celsius + 273.15
            else -> celsius
        }
    }
}
