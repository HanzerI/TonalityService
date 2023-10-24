package com.example.diplombackend.controllers

import com.example.diplombackend.Tonality.Companion.toTonality
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*


@Controller
class AnalysisController(private val restTemplate: RestTemplate) {
    @GetMapping("/analysis")
    fun showAnalysisPage(model: Model): String {
        model["texts"] = ""
        model["results"] = mutableMapOf<String,String>()
        return "analysis"
    }

    @PostMapping("/analysis")
    fun analyzeTexts(@RequestParam("texts") texts: String, model: Model): String {
        val url = "http://my-py-service:5000/analyze"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestBody = mapOf("texts" to texts.split("\n"))

        val request = HttpEntity(requestBody, headers)
        val response: ResponseEntity<Map<*, *>> = restTemplate.postForEntity(url, request, Map::class.java)

        @Suppress("UNCHECKED_CAST")
        val results = (response.body?.get("results") as? Map<String, String> ?: emptyMap()).mapValues { it.value.toTonality() }

        model["results"] = results
        return "analysis"
    }


}