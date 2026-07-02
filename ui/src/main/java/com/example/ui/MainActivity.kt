package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.WeatherTopViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WeatherTopScreen() }
    }

    @Composable
    fun WeatherTopScreen(viewModel: WeatherTopViewModel = viewModel()) {
        val weatherState by viewModel.weatherStateFlow.collectAsState()

        Scaffold { padding ->
            Column(
                modifier = Modifier.padding(paddingValues = padding)
                                   .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherInfo(
                    weatherDrawableId = viewModel.fetchWeatherDrawableId(weather = weatherState.weather),
                    weatherColor = viewModel.fetchWeatherColor(weather = weatherState.weather)
                )
                Spacer(modifier = Modifier.height(height = 80.dp))
                ActionButtons(
                    onReload = { viewModel.reloadWeather(context = this@MainActivity) },
                    onNext = { /*TODO*/ }
                )
            }
            if(weatherState.showErrorDialog){
                ErrorDialog(
                    onDismiss = { viewModel.dismissErrorDialog() },
                    onReload = { viewModel.reloadWeather(context = this@MainActivity) }
                )
            }
        }
    }

    @Composable
    private fun WeatherInfo(weatherDrawableId: Int, weatherColor: Color) {
        Column {
            Image(
                painter = painterResource(id = weatherDrawableId),
                contentDescription = "Weather Image",
                modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                                   .aspectRatio(ratio = 1f),
                colorFilter = ColorFilter.tint(color = weatherColor)
            )
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

    @Preview
    @Composable
    fun WeatherInfoSunnyPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            WeatherInfo(
                weatherDrawableId = R.drawable.sunny,
                weatherColor = Color.Red
            )
        }
    }

    @Preview
    @Composable
    fun WeatherInfoCloudyPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            WeatherInfo(
                weatherDrawableId = R.drawable.cloudy,
                weatherColor = Color.Gray
            )
        }
    }

    @Preview
    @Composable
    fun WeatherInfoRainyPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            WeatherInfo(
                weatherDrawableId = R.drawable.rainy,
                weatherColor = Color.Blue
            )
        }
    }

    @Preview
    @Composable
    fun WeatherInfoSnowPreview() {
        MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Red)) {
            WeatherInfo(
                weatherDrawableId = R.drawable.snow,
                weatherColor = Color.White
            )
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
