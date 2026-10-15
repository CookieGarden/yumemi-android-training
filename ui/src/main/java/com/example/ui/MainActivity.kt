package com.example.ui
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.ui.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WeatherTopScreen() }
    }

    @Composable
    fun WeatherTopScreen(viewModel: WeatherTopViewModel = hiltViewModel()) {
        val weatherState by viewModel.weatherStateFlow.collectAsState()

        Scaffold { padding ->
            Box(
                modifier = Modifier.padding(paddingValues = padding)
                                   .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val weather = weatherState.weather
                    WeatherInfo(
                        weatherDrawableId = viewModel.fetchWeatherDrawableId(weather = weather?.weather),
                        weatherColor = viewModel.fetchWeatherColor(weather = weather?.weather),
                        area = weather?.area,
                        minTemp = weather?.minTemp,
                        maxTemp = weather?.maxTemp
                    )
                    Spacer(modifier = Modifier.height(height = 80.dp))
                    ActionButtons(
                        onReload = { viewModel.reloadWeather() },
                        onNext = { /*TODO*/ }
                    )
                }
                if(weatherState.isLoading){
                    LoadingIndicator(modifier = Modifier.matchParentSize())
                }
            }
            if(weatherState.showErrorDialog){
                ErrorDialog(
                    onDismiss = { viewModel.dismissErrorDialog() },
                    onReload = { viewModel.reloadWeather() }
                )
            }
        }
    }

    @Composable
    private fun WeatherInfo(
        weatherDrawableId: Int,
        weatherColor: Color,
        area: String?,
        minTemp: Int?,
        maxTemp: Int?,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = area ?: "--")
            Image(
                painter = painterResource(id = weatherDrawableId),
                contentDescription = "Weather Image",
                modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                                   .aspectRatio(ratio = 1f),
                colorFilter = ColorFilter.tint(color = weatherColor)
            )
            Row(modifier = Modifier.fillMaxWidth(fraction = 0.5f)) {
                Text(
                    text = minTemp?.let { "$it℃" } ?: "--",
                    color = Color.Blue,
                    modifier = Modifier.weight(weight = 1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = maxTemp?.let { "$it℃" } ?: "--",
                    color = Color.Red,
                    modifier = Modifier.weight(weight = 1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    private fun ActionButtons(onReload: () -> Unit, onNext: () -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            Button(
                onClick = { onReload() },
                modifier = Modifier.weight(weight = 1f),
                shape = RoundedCornerShape(size = 4.dp),
                contentPadding = PaddingValues(all = 8.dp)
            ) {
                Text(text = "RELOAD")
            }
            Button(
                onClick = { onNext() },
                modifier = Modifier.weight(weight = 1f),
                shape = RoundedCornerShape(size = 4.dp)
            ) {
                Text(text = "NEXT")
            }
        }
    }

    @Composable
    private fun LoadingIndicator(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                               .pointerInput(key1 = Unit) { detectTapGestures { } },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(size = 80.dp))
        }
    }

    @Composable
    private fun ErrorDialog(onDismiss: () -> Unit, onReload: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Error") },
            text = { Text(text = "エラーが発生しました。") },
            confirmButton = { TextButton(onClick = { onReload() }) { Text(text = "Reload") } },
            dismissButton = { TextButton(onClick = { onDismiss() }) { Text(text = "Close") } },
        )
    }

    @Preview
    @Composable
    fun WeatherTopScreenPreview() {
        WeatherTopScreen()
    }

    @Composable
    private fun WeatherInfoPreview(weatherDrawableId: Int, weatherColor: Color) {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = weatherColor)) {
            WeatherInfo(
                weatherDrawableId = weatherDrawableId,
                weatherColor = weatherColor,
                area = "東京",
                minTemp = 10,
                maxTemp = 25
            )
        }
    }

    @Preview
    @Composable
    fun WeatherInfoSunnyPreview() {
        WeatherInfoPreview(weatherDrawableId = R.drawable.sunny, weatherColor = Color.Red)
    }

    @Preview
    @Composable
    fun WeatherInfoCloudyPreview() {
        WeatherInfoPreview(weatherDrawableId = R.drawable.cloudy, weatherColor = Color.Gray)
    }

    @Preview
    @Composable
    fun WeatherInfoRainyPreview() {
        WeatherInfoPreview(weatherDrawableId = R.drawable.rainy, weatherColor = Color.Blue)
    }

    @Preview
    @Composable
    fun WeatherInfoSnowPreview() {
        WeatherInfoPreview(weatherDrawableId = R.drawable.snow, weatherColor = Color.White)
    }

    @Preview
    @Composable
    fun LoadingIndicatorPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
    }

    @Preview
    @Composable
    fun ActionButtonsPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            ActionButtons(onReload = {}, onNext = {})
        }
    }
}
