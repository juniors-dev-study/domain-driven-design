package com.sns.user.core.infrastructures.mail

import java.nio.charset.StandardCharsets
import java.util.*
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringTemplateEngine

@Service
class MailService(
    val javaMailSender: JavaMailSender,
    val templateEngine: ISpringTemplateEngine,
    @Value("\${spring.mail.username}") val fromId: String
) {
    /**
     * 가입 인증코드 메일 발송
     * @param authCode 인증 코드
     * @param toAddress 수신인 주소
     */
    fun sendSignUpAuthCodeMail(authCode: String, toAddress: String) {
        javaMailSender.send(javaMailSender.createMimeMessage().setBase("가입 인증 코드", createSignUpAuthCodeMailTemplate(authCode), toAddress))
    }

    private fun createSignUpAuthCodeMailTemplate(authCode: String): String =
        templateEngine.process("signUpAuthCode", Context(Locale.KOREAN, mapOf<String, Any>("code" to authCode)))

    fun MimeMessage.setBase(title: String, content: String, toAddress: String): MimeMessage {
        setRecipient(Message.RecipientType.TO, InternetAddress(toAddress))
        setSubject("[DDD SNS] $title", StandardCharsets.UTF_8.displayName())
        setContent(content, "text/html;charset=euc-kr")
        return this
    }
}
