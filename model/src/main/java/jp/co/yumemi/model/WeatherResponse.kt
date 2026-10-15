package jp.co.yumemi.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val weather: String,
    val maxTemp: Int,
    val minTemp: Int,
    val date: String,
    val area: String
)
