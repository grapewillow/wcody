package com.example.wcody.dto.weather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.*

data class WeatherApiResponse(
    val header: Header? = null,
    val body: Body? = null
)

data class Header(
    val resultCode: String? = null,
    val resultMsg: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Body(
    val dataType: String? = null,
    val items: Items? = null,
    val numOfRows: Int? = null,
    val pageNo: Int? = null,
    val totalCount: Int? = null
)

data class Items(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    val item: List<ForecastItem> = emptyList()
)

data class ForecastItem(
    val category: String,
    val fcstValue: String,
    val fcstDate: String? = null,
    val fcstTime: String? = null,
    val baseDate: String? = null,
    val baseTime: String? = null,
    val nx: Int? = null,
    val ny: Int? = null
)