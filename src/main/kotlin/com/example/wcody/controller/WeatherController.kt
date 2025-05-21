package com.example.wcody.controller

import com.example.wcody.repository.UserRepository
import com.example.wcody.service.WeatherService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.NumberFormatException

class WeatherController(
    private val weatherService: WeatherService
) {
    fun registerRoutes(route: Route) {
        route.route("/weather") {
            get {
                val userIdParam = call.request.queryParameters["userId"]
                val userId: Long = try {
                    userIdParam?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest, "userId 누락")
                } catch (e: NumberFormatException) {
                    return@get call.respond(HttpStatusCode.BadRequest, "userId는 숫자여야 합니다.")
                }

                try {
                    val weather = weatherService.getWeatherForUser(userId)
                    call.respond(weather)
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "정보 없음")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "서버 오류")
                }
            }
        }
    }
}