package jp.co.yumemi.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherRequest(
    val area: String,
    val date: String
)
