package com.hust.currency_converter;

object CurrencyConverter {
    // Tỷ giá so với USD
    private val ratesToUSD = mapOf(
        "USD" to 1.0,
        "EUR" to 1.18, // 1 EUR = 1.18 USD
        "JPY" to 0.0091, // 1 JPY = 0.0091 USD
        "VND" to 0.000043, // 1 VND = 0.000043 USD
        "CNY" to 0.15, // 1 CNY = 0.15 USD
        "KRW" to 0.00085 // 1 KRW = 0.00085 USD
    )

    // Hàm lấy tỷ giá chuyển đổi thông qua USD
    fun getConversionRate(source: String, target: String): Double {
        val rateToUSD = ratesToUSD[source] ?: return 1.0
        val rateFromUSD = ratesToUSD[target]?.let { 1 / it } ?: return 1.0
        return rateToUSD * rateFromUSD
    }

    // Hàm thực hiện chuyển đổi
    fun convert(amount: Double, source: String, target: String): Double {
        val conversionRate = getConversionRate(source, target)
        return amount * conversionRate
    }
}


