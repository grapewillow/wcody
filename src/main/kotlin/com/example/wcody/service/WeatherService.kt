package com.example.wcody.service

import com.example.wcody.com.example.wcody.dto.WeatherData
import com.example.wcody.com.example.wcody.repository.RegionRepository
import com.example.wcody.dto.weather.WeatherApiResponse
import com.example.wcody.model.Region
import com.example.wcody.repository.UserRepository
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.config.*
import io.ktor.server.plugins.*
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.wcody.util.getSkyImageFileName

class WeatherService(private val config: ApplicationConfig, private val regionRepository: RegionRepository, private val userRepository: UserRepository, private val client: HttpClient) {

    val apiKey = config.property("weather.apiKey").getString()
    val baseUrl = config.property("weather.baseUrl").getString()

    suspend fun getWeather(region: Region): WeatherData? {
        val now = LocalDateTime.now()
        val baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val baseTime = getBaseTime(now.hour)

        val url = "$baseUrl/getVilageFcst?serviceKey=${apiKey}" +
                "&pageNo=1&numOfRows=10&pageNo=1" +
                "&base_date=$baseDate&base_time=$baseTime&nx=${region.nx}&ny=${region.ny}"
        return try {
            val response: WeatherApiResponse = client.get(url) {
                headers {
                    append("Accept", "application/xml")
                }
            }.body()

            if (response.header?.resultCode != "00") {
                println("기상청 API 오류: ${response.header?.resultMsg}")
                return null
            }

            val items = response.body?.items?.item ?: emptyList()

            var temp: Double? = null
            var skyCode: String? = null
            var ptyCode: String? = null
            var pop: String? = null
            var pcp: String? = null
            var reh: String? = null
            var uuu: String? = null
            var vvv: String? = null
            var vec: String? = null
            var wsd: String? = null

            for (item in items) {
                when (item.category) {
                    "TMP" -> temp = item.fcstValue.toDoubleOrNull()
                    "SKY" -> skyCode = item.fcstValue
                    "PTY" -> ptyCode = item.fcstValue
                    "POP" -> pop = item.fcstValue
                    "PCP" -> pcp = item.fcstValue
                    "REH" -> reh = item.fcstValue
                    "UUU" -> uuu = item.fcstValue
                    "VVV" -> vvv = item.fcstValue
                    "VEC" -> vec = item.fcstValue
                    "WSD" -> wsd = item.fcstValue
                }
            }

            val description = when (ptyCode) {
                "1", "4" -> "비"
                "2" -> "비눈"
                "3" -> "눈"
                "5", "6", "7" -> "강수"
                "0", null -> when (skyCode) {
                    "1" -> "맑음"
                    "3" -> "구름많음"
                    "4" -> "흐림"
                    else -> "정보없음"
                }
                else -> "정보없음"
            }

            if (temp != null) {
                WeatherData(
                    temp = temp,
                    description = description,
                    icon = getSkyImageFileName(description, now.hour),
                    name = listOfNotNull(region.sido, region.sigungu, region.dong).joinToString(" "),
                    pop = pop,
                    pcp = pcp,
                    reh = reh,
                    uuu = uuu,
                    vvv = vvv,
                    vec = vec,
                    wsd = wsd,
                    pcpLevel = getPrecipitationLevel(pcp),
                    windStrength = getWindStrength(wsd)
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getBaseTime(hour: Int): String {
        return when {
            hour < 2 -> "2300"
            hour < 5 -> "0200"
            hour < 8 -> "0500"
            hour < 11 -> "0800"
            hour < 14 -> "1100"
            hour < 17 -> "1400"
            hour < 20 -> "1700"
            hour < 23 -> "2000"
            else -> "2300"
        }
    }

    suspend fun getWeatherForUser(userId: Long): WeatherData {
        val user = userRepository.findById(userId) ?: throw NotFoundException("사용자 없음")
        val weather = getWeather(user.region) ?: throw IllegalStateException("날씨 정보를 가져오지 못했습니다.")
        return weather
    }

    fun getPrecipitationLevel(pcp: String?): String {
        return when {
            pcp == null || pcp == "강수없음" -> "0"
            pcp.endsWith("mm") -> {
                val value = pcp.replace("mm", "").trim().toDoubleOrNull()
                when {
                    value == null -> "강수 정보 없음"
                    value < 3 -> "약한 비"
                    value < 15 -> "보통 비"
                    else -> "강한 비"
                }
            }
            else -> "강수 정보 없음"
        }
    }

    fun getWindStrength(wsd: String?): String {
        val value = wsd?.toDoubleOrNull()
        return when {
            value == null -> "바람 정보 없음"
            value < 4 -> "약한 바람"
            value < 9 -> "약간 강한 바람"
            else -> "강한 바람"
        }
    }
}