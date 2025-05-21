package com.example.wcody.com.example.wcody.dto

data class WeatherData(
    val temp: Double,
    val description: String,
    val icon: String,
    val name: String,
    val pop: String?,      // 강수 확률
    val pcp: String?,      // 강수량
    val reh: String?,      // 습도
    val uuu: String?,      // 동서풍
    val vvv: String?,      // 남북풍
    val vec: String?,      // 풍향
    val wsd: String?,       // 풍속
    val pcpLevel: String?,  // 강수량 해석
    val windStrength: String? // 바람세기 해석
)