package com.example.diplombackend.controllers


import com.example.diplombackend.entitys.User
import com.example.diplombackend.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDate

@Controller
class RegistrationController(private val userService: UserService, ) {
    private val logger: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model["user"] = User::class.java.getDeclaredConstructor().newInstance()
        return "registration"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute("user") user: User, model: Model): String {
        if (userService.getUserByUsername(user.username) != null) {
            model["usernameError"] = "Username is already in use"
            return "registration"
        }

        if (userService.getUserByEmail(user.email) != null) {
            model["emailError"] = "Email is already in use"
            return "registration"
        }

        userService.createUser(user)
        logger.info("Registered user: {} on {}", user.username, LocalDate.now())
        return "redirect:/login"
    }
}
