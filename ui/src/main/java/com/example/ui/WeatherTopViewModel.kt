package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.ui.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeatherTopViewModel @Inject constructor(
    private val yumemiWeather: YumemiWeather
) : ViewModel() {

    private val _weatherMutableStateFlow = MutableStateFlow<WeatherState>(value = WeatherState(weather = null, showErrorDialog = false))
    val weatherStateFlow: StateFlow<WeatherState> = _weatherMutableStateFlow.asStateFlow()

    fun reloadWeather(): Unit {
        try {
            val weather = yumemiWeather.fetchThrowsWeather()
            _weatherMutableStateFlow.update {
                it.copy(weather = weather, showErrorDialog = false)
            }

        } catch (e: UnknownException) {
            _weatherMutableStateFlow.update {
                it.copy(showErrorDialog = true)
            }
        }
    }

    fun fetchWeatherDrawableId(weather: String?): Int {
        return when(weather) {
            "sunny" -> R.drawable.sunny
            "cloudy" -> R.drawable.cloudy
            "rainy" -> R.drawable.rainy
            "snow" -> R.drawable.snow
            else -> R.drawable.sunny
        }
    }

    fun fetchWeatherColor(weather: String?): Color {
        return when(weather) {
            "sunny" -> Color.Red
            "cloudy" -> Color.Gray
            "rainy" -> Color.Blue
            "snow" -> Color.White
            else -> Color.Red
        }
    }

    fun dismissErrorDialog(): Unit {
        _weatherMutableStateFlow.update { it.copy(showErrorDialog = false) }
    }
}
