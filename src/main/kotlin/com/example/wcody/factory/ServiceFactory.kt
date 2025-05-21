package com.example.wcody.factory

import com.example.wcody.config.SmtpConfig
import com.example.wcody.service.*

object ServiceProvider {
    fun createAuthService(): AuthService {
        val em = com.example.wcody.util.HibernateUtil.entityManagerFactory.createEntityManager()
        val tokenStore = InMemoryTokenStore()
        val emailService = SmtpEmailService(
            smtpHost = SmtpConfig.host,
            smtpPort = SmtpConfig.port,
            username = SmtpConfig.username,
            password = SmtpConfig.password
        )
        return AuthService(em, tokenStore, emailService)
    }
}