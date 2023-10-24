package com.example.diplombackend.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PrivateController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/private")
    fun privatePage(): String {
        return "private"
    }
}
