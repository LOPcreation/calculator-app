package com.example.calculator.logic

import com.example.calculator.ui.screens.UnitCategory
import org.junit.Test
import org.junit.Assert.*

class UnitConverterTest {

    @Test
    fun testLengthConversion() {
        val converter = UnitConverter()
        val result = converter.convert(UnitCategory.LENGTH, 1000.0, "m", "km")
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun testTemperatureConversion() {
        val converter = UnitConverter()
        val result = converter.convert(UnitCategory.TEMPERATURE, 0.0, "°C", "°F")
        assertEquals(32.0, result, 0.001)
    }

    @Test
    fun testWeightConversion() {
        val converter = UnitConverter()
        val result = converter.convert(UnitCategory.WEIGHT, 1000.0, "g", "kg")
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun testGetUnits() {
        val converter = UnitConverter()
        val units = converter.getUnits(UnitCategory.LENGTH)
        assertTrue(units.contains("m"))
        assertTrue(units.contains("km"))
    }
}
