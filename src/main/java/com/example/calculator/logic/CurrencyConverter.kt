package com.example.calculator.logic

import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ConversionResult(
    val convertedAmount: Double,
    val exchangeRate: Double,
    val timestamp: String
)

class CurrencyConverter {
    val supportedCurrencies = listOf(
        "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", 
        "INR", "MXN", "SGD", "HKD", "NOK", "SEK", "NZD", "MYR", 
        "PHP", "THB", "VND", "IDR", "KRW", "TWD", "BRL", "ZAR",
        "TRY", "RUB", "AED", "SAR", "QAR", "KWD", "BHD"
    )

    private val client = OkHttpClient()
    private val cache = mutableMapOf<String, CachedRate>()
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    data class CachedRate(
        val rate: Double,
        val timestamp: Long
    )

    suspend fun convert(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ): ConversionResult = withContext(Dispatchers.IO) {
        try {
            val exchangeRate = getExchangeRate(fromCurrency, toCurrency)
            val convertedAmount = amount * exchangeRate
            val timestamp = LocalDateTime.now().format(formatter)

            ConversionResult(
                convertedAmount = round(convertedAmount, 2),
                exchangeRate = round(exchangeRate, 6),
                timestamp = timestamp
            )
        } catch (e: Exception) {
            throw Exception("Failed to fetch exchange rates: ${e.message}")
        }
    }

    private suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): Double = withContext(Dispatchers.IO) {
        val cacheKey = "$fromCurrency-$toCurrency"
        val now = System.currentTimeMillis()

        // Use cache if available and less than 1 hour old
        val cached = cache[cacheKey]
        if (cached != null && (now - cached.timestamp) < 3600000) {
            return@withContext cached.rate
        }

        // Fetch from API
        val rate = fetchFromExchangeRateApi(fromCurrency, toCurrency)
        cache[cacheKey] = CachedRate(rate, now)
        return@withContext rate
    }

    private fun fetchFromExchangeRateApi(fromCurrency: String, toCurrency: String): Double {
        // Using exchangerate-api.com free tier
        val url = "https://api.exchangerate-api.com/v4/latest/$fromCurrency"
        val request = Request.Builder()
            .url(url)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception("API call failed with code ${response.code}")
                }

                val body = response.body?.string() ?: throw Exception("Empty response")
                val jsonObject = JsonParser.parseString(body).asJsonObject
                val rates = jsonObject.getAsJsonObject("rates")
                rates.get(toCurrency)?.asDouble ?: throw Exception("Currency not found")
            }
        } catch (e: Exception) {
            // Fallback to mock data if API fails
            getMockExchangeRate(fromCurrency, toCurrency)
        }
    }

    private fun getMockExchangeRate(fromCurrency: String, toCurrency: String): Double {
        // Fallback mock exchange rates
        val rates = mapOf(
            "USD" to mapOf(
                "EUR" to 0.92,
                "GBP" to 0.79,
                "JPY" to 149.50,
                "INR" to 83.12,
                "AUD" to 1.52,
                "CAD" to 1.36
            ),
            "EUR" to mapOf(
                "USD" to 1.09,
                "GBP" to 0.86,
                "JPY" to 162.50,
                "INR" to 90.25,
                "AUD" to 1.65,
                "CAD" to 1.48
            )
        )

        val fromRates = rates[fromCurrency] ?: mapOf()
        return fromRates[toCurrency] ?: 1.0
    }

    private fun round(value: Double, decimals: Int): Double {
        return kotlin.math.round(value * Math.pow(10.0, decimals.toDouble())) / Math.pow(10.0, decimals.toDouble())
    }
}
