package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jp.co.yumemi.api.YumemiWeather
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTopScreen()
        }
    }

    @Composable
    fun WeatherTopScreen() {
        Scaffold { padding ->
            var weather by remember { mutableStateOf<String?>(null) }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherInfo(weather = weather)
                Spacer(modifier = Modifier.height(80.dp))
                ActionButtons(
                    onReload = {
                        weather = YumemiWeather(this@MainActivity).fetchSimpleWeather()
                    },
                    onNext = { /*TODO*/ }
                )
            }
        }
    }

    @Composable
    private fun WeatherInfo(weather: String?){
        Column {
            Image(
                painter = painterResource(
                    id = when (weather) {
                        "sunny" -> R.drawable.sunny
                        "cloudy" -> R.drawable.cloudy
                        "rainy" -> R.drawable.rainy
                        "snow" -> R.drawable.snow
                        else -> R.drawable.sunny
                    }
                ),
                contentDescription = "Weather Image",
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .aspectRatio(1f),
                colorFilter = ColorFilter.tint(
                    color = when (weather) {
                        "sunny" -> Color.Red
                        "cloudy" -> Color.Gray
                        "rainy" -> Color.Blue
                        "snow" -> Color.White
                        else -> Color.Red
                    }
                )
            )
        }
    }
    @Composable
    private fun ActionButtons(onReload: () -> Unit, onNext: () -> Unit){
        Row(
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onReload() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text ="RELOAD",
                    softWrap = false
                )
            }
            Button(
                onClick = { onNext() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("NEXT")
            }
        }
    }
}
