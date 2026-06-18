package com.example.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.ui.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WeatherTopViewModel() : ViewModel() {

    // 状態を持つ変数が必要
    // MainActivityで状態を更新している部分をここに移動する
    // MainActivityでこれらを参照する

//    var weatherState = mutableStateOf<WeatherState>(WeatherState(null, false))

    // mutableStateOf vs MutableStateFlow
    // https://at-sushi.work/blog/24/
    // Mutableで良いか? privateで閉じる?
    // だがmainActivityから呼びたい StateFlowも用意する
    // MutableStateFlow: 内だけで更新したい(setter)
    private var weatherMutableStateFlow = MutableStateFlow<WeatherState>(WeatherState(null, false))

    // StateFlow: 外部で参照したい(getter)
    val weatherStateFlow: StateFlow<WeatherState> = weatherMutableStateFlow.asStateFlow()

    fun reloadWeather(context: Context): Unit {
        try {
            val weather = YumemiWeather(context).fetchThrowsWeather()
            weatherMutableStateFlow.update {
                it.copy(weather = weather, showErrorDialog = false)
            }

        } catch (e: UnknownException) {
            weatherMutableStateFlow.update {
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

    // colorを出し分けている箇所もここに移動
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
        weatherMutableStateFlow.update { it.copy(showErrorDialog = false) }
    }
}
