package com.example.wcody.service

import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SmtpEmailService(
    private val smtpHost: String,
    private val smtpPort: String,
    private val username: String,
    private val password: String
) : EmailService {

    override fun sendPasswordResetEmail(to: String, resetUrl: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", smtpHost)
            put("mail.smtp.port", smtpPort)
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                subject = "비밀번호 재설정 안내"
                setText("다음 링크를 클릭하여 비밀번호를 재설정하세요:\n$resetUrl")
            }

            Transport.send(message)
            println("📧 이메일 전송 완료: $to")
        } catch (e: MessagingException) {
            e.printStackTrace()
            throw RuntimeException("이메일 전송 실패: ${e.message}")
        }
    }
}
