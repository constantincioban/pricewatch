package com.konovus.network.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.Part
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.content
import com.konovus.network.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class ApiClient {

    private val defaultConfigPrompt =
        "You will be provided with HTML pages containing product information from various websites. " +
                "Your task is to extract and return only the price of the main product, formatted as '18.99$', " +
                "including the currency symbol." +
                "Do not include any other text or explanation. at the end check that every character is a digit or a currency symbol."

    private val llm = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey,
        systemInstruction = Content("price extraction", listOf<Part>(TextPart(defaultConfigPrompt)))
    )

    suspend fun fetchHtml(url: String): String {
        return withContext(Dispatchers.IO) {
            Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .referrer("http://www.google.com")
                .get().body().html()
        }
    }

    suspend fun promptLLM(prompt: String): String {
        val response = llm.generateContent(prompt)
        return response.text ?: "No response from Gemini API"
    }
}