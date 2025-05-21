package com.example.wcody.service

interface PasswordResetTokenStore {
    fun save(userId: Long, token: String)
    fun get(userId: Long): String?
}