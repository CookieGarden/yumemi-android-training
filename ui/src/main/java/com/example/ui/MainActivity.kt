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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                    ActionButtons(onReload = {
                        weather = YumemiWeather(this@MainActivity).fetchSimpleWeather()
                    }, onNext = { /*TODO*/ })
                }
            }
        }
    }

    @Composable
    private fun WeatherInfo(weather: String? = null){
        Column {
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .aspectRatio(1f),
                colorFilter = ColorFilter.tint(color = Color.Red)
            )
            Row(modifier = Modifier.fillMaxWidth(fraction = 0.5f)) {
                Text(weather.toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                Text("text", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }
    }
    @Composable
    private fun ActionButtons(onReload: () -> Unit, onNext: () -> Unit){
        Row(modifier = Modifier.fillMaxWidth(fraction = 0.5f)) {
            Button(onClick = { onReload() }, modifier = Modifier.weight(1f)) {
                Text("RELOAD")
            }
            Button(onClick = { onNext() }, modifier = Modifier.weight(1f)) {
                Text("NEXT")
            }
        }
    }
}
