package com.example.diplombackend.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {

    @GetMapping("/login")
    fun showLoginForm(): String {
        return "login"
    }
}
