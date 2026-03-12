package com.example.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.ui.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherTopViewModel() : ViewModel() {

    // 状態を持つ変数が必要
    // MainActivityで状態を更新している部分をここに移動する
    // MainActivityでこれらを参照する

    // YumemiWeatherを叩いている部分をこっちに移動する

    fun convertToWeatherDrawable(weatherState: WeatherState): Int {
        return when(weatherState.weather) {
            "sunny" -> R.drawable.sunny
            "cloudy" -> R.drawable.cloudy
            "rainy" -> R.drawable.rainy
            "snow" -> R.drawable.snow
            else -> R.drawable.sunny
        }
    }

    // colorを出し分けている箇所もここに移動
}
