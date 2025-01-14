package com.konovus.pricewatchai

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.Part
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BakingViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val defaultConfigPrompt =
        "You will be provided with HTML pages containing product information from various websites. " +
                "Your task is to extract and return only the price of the main product, formatted as '18.99$', " +
                "including the currency symbol." +
                "Do not include any other text or explanation. at the end check that every character is a digit or a currency symbol."

    private val generativeModel =
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.apiKey,
            systemInstruction = Content("price extraction", listOf<Part>(TextPart(defaultConfigPrompt)))
        )

    fun sendPrompt(
        bitmap: Bitmap,
        prompt: String
    ) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text("<div class=\"a-section a-spacing-none aok-align-center aok-relative\"> <span class=\"aok-offscreen\">   &pound;18.99 with 58 percent savings    </span>          <span aria-hidden=\"true\" class=\"a-size-large a-color-price savingPriceOverride aok-align-center reinventPriceSavingsPercentageMargin savingsPercentage\">-58%</span>           <span class=\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\" data-a-size=\"xl\" data-a-color=\"base\"><span class=\"a-offscreen\"> </span><span aria-hidden=\"true\"><span class=\"a-price-symbol\">£</span><span class=\"a-price-whole\">18<span class=\"a-price-decimal\">.</span></span><span class=\"a-price-fraction\">99</span></span></span>              <span id=\"taxInclusiveMessage\" class=\"a-size-mini a-color-base aok-align-center aok-nowrap\">  </span>                </div>   <div class=\"a-section a-spacing-small aok-align-center\">      <span> <span class=\"aok-relative\"><span class=\"a-size-small aok-offscreen\"> RRP: £44.99 </span><span aria-hidden=\"true\" class=\"a-size-small a-color-secondary aok-align-center basisPrice\">RRP:      <span class=\"a-price a-text-price\" data-a-size=\"s\" data-a-strike=\"true\" data-a-color=\"secondary\"><span class=\"a-offscreen\">£44.99</span><span aria-hidden=\"true\">£44.99</span></span>   </span></span>   <span class=\"a-size-small aok-align-center basisPriceLegalMessage\">                        <span class=\"a-declarative\" data-action=\"a-popover\" data-a-popover=\"{&quot;closeButton&quot;:&quot;true&quot;,&quot;name&quot;:&quot;basisPriceLegalMessageDisplayPreload-e187dd8e-bcc9-4587-9e33-eb905bf241e0&quot;,&quot;position&quot;:&quot;triggerBottom&quot;}\">   <a class=\"a-align-center a-link-normal aok-inline-block\" href=\"#\">   <img role=\"img\" aria-label=\"Learn more about Amazon pricing and savings\" height=\"15\" width=\"12\" src=\"https://m.media-amazon.com/images/S/sash//GN8m8-lU2_Dj38v.svg\"/>    </a>    </span> <div class=\"a-popover-preload\" id=\"a-popover-basisPriceLegalMessageDisplayPreload-e187dd8e-bcc9-4587-9e33-eb905bf241e0\">   <div aria-label=\"Details\" aria-modal=\"true\" class=\"a-section a-spacing-none\" role=\"dialog\">  <span class=\"a-size-base\">The <b>RRP</b> is the suggested or recommended retail price of a product set by the manufacturer and provided by a manufacturer, supplier or seller.<br/><a class=\"a-link-normal\" href=\"https://www.amazon.co.uk/gp/help/customer/display.html?nodeId=GQ6B6RH72AX8D2TD\">Learn more</a></span> </div>    </div>    <style type=\"text/css\">\n")
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}