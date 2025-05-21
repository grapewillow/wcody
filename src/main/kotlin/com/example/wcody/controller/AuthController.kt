package com.example.wcody.controller

import com.example.wcody.config.SmtpConfig
import com.example.wcody.dto.LoginRequest
import com.example.wcody.dto.FindPasswordRequest
import com.example.wcody.dto.RegisterRequest
import com.example.wcody.factory.ServiceProvider
import com.example.wcody.service.AuthService
import com.example.wcody.service.InMemoryTokenStore
import com.example.wcody.service.SmtpEmailService
import com.example.wcody.util.HibernateUtil
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthController {
    fun registerRoutes(route: Route) {
        route.route("/auth") {

            post("/login") {
                val authService = ServiceProvider.createAuthService()
                val request = call.receive<LoginRequest>()
                val response = authService.login(request)
                authService.close()  // EntityManager 정리
                call.respond(response)
            }

            post("/password") {
                val authService = ServiceProvider.createAuthService()
                val request = call.receive<FindPasswordRequest>()
                val response = authService.findPassword(request)
                authService.close()
                call.respond(response)
            }
        }
    }
}
