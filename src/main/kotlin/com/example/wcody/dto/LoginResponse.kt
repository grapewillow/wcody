package com.example.wcody.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)