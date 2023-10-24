package com.example.diplombackend


import com.example.diplombackend.services.UserService
import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig{
    @Autowired
    @Resource
    lateinit var  passwordEncoder: PasswordEncoder
    @Autowired
    @Resource
    lateinit var  userService: UserService


    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }



    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/public", permitAll)
                authorize("/private", hasRole("ROLE_USER"))
                authorize("/profile", hasRole("ROLE_USER"))
                authorize("/analysis", permitAll)
                authorize("/analyze", permitAll)
                authorize("/auth", hasRole("ROLE_USER"))
                authorize("/auth/vk", hasRole("ROLE_USER"))
            }
            formLogin {
                loginPage = "/login"
                permitAll()
                failureUrl = "/login?error"
                defaultSuccessUrl("/private", true) // Перенаправление на /private после успешной аутентификации
            }
            logout {
                permitAll()
            }
        }
        return http.build()
    }

}
