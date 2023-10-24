package com.example.diplombackend.controllers
import com.example.diplombackend.entitys.User
import com.example.diplombackend.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @PostMapping("/users")
    fun addUser(@RequestBody user: User) {
        userService.saveUser(user)
    }
}
