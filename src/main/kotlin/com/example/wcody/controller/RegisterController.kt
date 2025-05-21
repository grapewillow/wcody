package com.example.wcody.controller

import com.example.wcody.com.example.wcody.repository.RegionRepository
import com.example.wcody.dto.RegisterRequest
import com.example.wcody.factory.ServiceProvider
import com.example.wcody.repository.UserRepository
import com.example.wcody.service.AuthService
import com.example.wcody.util.HibernateUtil
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class RegisterController(
    private val userRepository: UserRepository,
    private val regionRepository: RegionRepository
) {
    fun registerRoutes(route: Route) {
        route.route("/register") {
            get("/regions") {
                println("region")
                val regions = regionRepository.findAll()
                call.respond(regions)
            }

            post("") {
                val em = HibernateUtil.entityManagerFactory.createEntityManager()
                val authService = ServiceProvider.createAuthService()
                val request = call.receive<RegisterRequest>()
                val response = authService.register(request)
                em.close()
                call.respond(response)
            }
        }
    }
}