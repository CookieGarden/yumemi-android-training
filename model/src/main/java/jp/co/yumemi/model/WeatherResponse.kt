package jp.co.yumemi.model

data class WeatherResponse(
    val weather: String,
    val maxTemp: Int,
    val minTemp: Int,
    val date: String,
    val area: String
)
