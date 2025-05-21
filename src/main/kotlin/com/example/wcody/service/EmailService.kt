package com.example.wcody.service

interface EmailService {
    fun sendPasswordResetEmail(to: String, resetUrl: String)
}