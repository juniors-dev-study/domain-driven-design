package com.sns.user.core.infrastructures.mail

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// https://myaccount.google.com/lesssecureapps 보안이 낮은 수준의 앱 액세스 허용 필요.
@SpringBootTest(
    properties = [
        "spring.mail.username=***@gmail.com",
        "spring.mail.password=***",
    ],
)
class MailServiceTest @Autowired constructor(
    val mailService: MailService
) {

    @Disabled("진짜 메일 발송용")
    @Test
    internal fun sendSignUpAuthCodeMail() {
        mailService.sendSignUpAuthCodeMail("ABC123", "***@gmail.com")
        // then checkout your mail
    }
}
