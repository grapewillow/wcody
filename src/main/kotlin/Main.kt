package com.example.wcody

import com.example.wcody.com.example.wcody.repository.RegionRepository
import com.example.wcody.config.HttpClientProvider
import com.example.wcody.controller.AuthController
import com.example.wcody.controller.MainController
import com.example.wcody.controller.RegisterController
import com.example.wcody.controller.WeatherController
import com.example.wcody.init.DataInitializer
import com.example.wcody.repository.UserRepository
import com.example.wcody.service.WeatherService
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.jackson.* // ✅ 클라이언트용
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.* // ✅ 서버용
import io.ktor.server.routing.*
import jakarta.persistence.Persistence
import org.slf4j.event.Level
import javax.xml.crypto.Data

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    // application.conf 설정 접근
    val config = HoconApplicationConfig(ConfigFactory.load())
    val emf = Persistence.createEntityManagerFactory("default")
    val em = emf.createEntityManager()

    val userRepository = UserRepository(em)
    val regionRepository = RegionRepository(em)
    val weatherServiceWithRepository = WeatherService(config, regionRepository, userRepository, HttpClientProvider.client)

    DataInitializer(regionRepository).initialize()

    install(CallLogging) {
        level = Level.INFO
    }
    // ✅ 서버용 JSON 직렬화 설정
    install(ContentNegotiation) {
        jackson()
    }

    // 라우팅 설정
    routing {
        AuthController().registerRoutes(this)
        MainController(weatherServiceWithRepository, userRepository).registerRoutes(this)
        RegisterController(userRepository, regionRepository).registerRoutes(this)
        WeatherController(weatherServiceWithRepository).registerRoutes(this)
    }
}

