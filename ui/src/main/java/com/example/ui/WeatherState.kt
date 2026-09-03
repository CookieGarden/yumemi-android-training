package com.example.ui

data class WeatherState(
    val weather: String?,
    val showErrorDialog: Boolean,
    val isLoading: Boolean = false,
)
