package com.example.wcody.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.wcody.service.PasswordResetTokenStore
import com.example.wcody.dto.*
import com.example.wcody.model.Region
import com.example.wcody.model.User
import com.example.wcody.repository.UserRepository
import com.example.wcody.util.JwtUtil
import jakarta.persistence.EntityManager
import java.util.UUID

class AuthService(private val em: EntityManager, private val tokenStore: PasswordResetTokenStore, private val emailService: EmailService) {
    private val userRepository = UserRepository(em)

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            ?: return LoginResponse(false, "사용자를 찾을 수 없습니다.")

        val result = BCrypt.verifyer().verify(request.password.toCharArray(), user.password)
        return if (result.verified) {
            val token = JwtUtil.generateToken(user.id.toString())
            LoginResponse(true, "로그인 성공", token)

        } else {
            LoginResponse(false, "비밀번호 불일치")
        }
    }

    fun register(request: RegisterRequest): RegisterResponse {
        val existingUser = userRepository.findByUsername(request.username)
        if (existingUser != null) {
            return RegisterResponse(false, "이미 존재하는 사용자입니다.")
        }

        if (request.password != request.confirmPassword) {
            return RegisterResponse(false, "비밀번호가 일치하지 않습니다.")
        }

        val region = em.find(Region::class.java, request.regionId)
            ?: return RegisterResponse(false, "지역 정보를 찾을 수 없습니다.")

        val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())

        val newUser = User(
            username = request.username,
            password = hashedPassword,
            nickname = request.nickname,
            email = request.email,
            birthYear = request.birthYear,
            birthMonth = request.birthMonth,
            birthDay = request.birthDay,
            region = region,
            gender = request.gender,
            sensitivity = request.sensitivity
        )

        em.transaction.begin()
        em.persist(newUser)
        em.transaction.commit()

        return RegisterResponse(true, "회원가입 성공")
    }

    fun findPassword(request: FindPasswordRequest): Map<String, String> {
        val user = userRepository.findByEmail(request.email)
            ?: return mapOf("message" to "가입된 이메일이 없습니다.")

        val token = UUID.randomUUID().toString()
        tokenStore.save(user.id, token)

        val resetUrl = "http://localhost:3001/auth/password?token=$token"
        emailService.sendPasswordResetEmail(user.email, resetUrl)

        return mapOf("message" to "비밀번호 재설정 링크가 이메일로 전송되었습니다.")
    }

    fun close() {
        em.close()
    }
}

