package com.example.wcody.config

import com.typesafe.config.ConfigFactory

object SmtpConfig {
    private val config = ConfigFactory.load().getConfig("ktor.smtp")

    val host: String = config.getString("host")
    val port: String = config.getString("port")
    val username: String = config.getString("username")
    val password: String = config.getString("password")
}