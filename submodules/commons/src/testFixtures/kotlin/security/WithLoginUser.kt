package security

import org.springframework.security.test.context.support.WithMockUser

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@WithMockUser(username = "user@gmail.com", authorities = ["read"])
annotation class WithLoginUser(
)
