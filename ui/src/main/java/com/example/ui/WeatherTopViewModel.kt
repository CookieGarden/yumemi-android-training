package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherTopViewModel @Inject constructor(
    private val yumemiWeather: YumemiWeather
) : ViewModel() {

    private val _weatherMutableStateFlow = MutableStateFlow<WeatherState>(value = WeatherState(weather = null, showErrorDialog = false))
    val weatherStateFlow: StateFlow<WeatherState> = _weatherMutableStateFlow.asStateFlow()

    fun reloadWeather() {
        viewModelScope.launch(context = Dispatchers.IO) {
            _weatherMutableStateFlow.update { it.copy(showErrorDialog = false, isLoading = true) }
            try {
                val weather = yumemiWeather.fetchWeatherAsync()
                _weatherMutableStateFlow.update { it.copy(weather = weather) }
            } catch (_: UnknownException) {
                _weatherMutableStateFlow.update { it.copy(showErrorDialog = true) }
            } finally {
                _weatherMutableStateFlow.update { it.copy(isLoading = false) }
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

    fun dismissErrorDialog() {
        _weatherMutableStateFlow.update { it.copy(showErrorDialog = false) }
    }
}
