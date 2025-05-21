package com.example.wcody.dto

data class RegisterRequest(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val nickname: String,
    val email: String,
    val birthYear: String,
    val birthMonth: String,
    val birthDay: String,
    val regionId: Long,
    val gender: String,
    val sensitivity: Int
)
