package com.example.ui

import jp.co.yumemi.model.WeatherResponse

data class WeatherState(
    val weather: WeatherResponse?,
    val showErrorDialog: Boolean,
    val isLoading: Boolean = false,
)
