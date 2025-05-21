package com.example.wcody.controller

import com.example.wcody.repository.UserRepository
import com.example.wcody.service.WeatherService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class MainController(private val weatherService: WeatherService, private val userRepository: UserRepository) {
    fun registerRoutes(route: Route) {
        route.route("/") {

        }
        route.route("/login") {
            println("login")
        }
        route.route("/main") {
            println("main")
        }
    }
}
