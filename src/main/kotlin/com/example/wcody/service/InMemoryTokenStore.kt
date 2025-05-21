package com.example.wcody.service

class InMemoryTokenStore : PasswordResetTokenStore {
    private val tokenMap = mutableMapOf<Long, String>()

    override fun save(userId: Long, token: String) {
        tokenMap[userId] = token
        println("✅ 토큰 저장: userId=$userId, token=$token")
    }

    override fun get(userId: Long): String? {
        return tokenMap[userId]
    }
}