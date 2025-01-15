package com.konovus.pricewatchai.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.konovus.network.data.ApiClient
import com.konovus.pricewatchai.BakingScreen
import com.konovus.pricewatchai.presentation.ui.theme.PriceWatchAITheme

class MainActivity : ComponentActivity() {

    private val apiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LaunchedEffect(Unit) {
                val html = apiClient.fetchHtml("https://www.amazon.co.uk/Lynx-Jungle-cleanser-refreshing-shower/dp/B0CNRZDSWQ?ref_=ast_sto_dp")
                println("html body: $html")
                println("price: ${apiClient.promptLLM(html)}")

            }

            PriceWatchAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BakingScreen()
                }
            }
        }
    }
}