package com.example.diplombackend.services


import com.example.diplombackend.entitys.Token
import com.example.diplombackend.entitys.User
import com.example.diplombackend.entitys.User.Companion.addToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
class UserService(val userRepository: UserRepository,val passwordEncoder: PasswordEncoder):UserDetailsService {

    fun saveToken(username: String?, token: Token) {
        val user = userRepository.findByUsername(username)
        user?.addToken(token)
        user?.let { userRepository.save(it) }
    }


    fun createUser(user: User) {
        user.password = passwordEncoder.encode(user.password)
        user.roles.add("ROLE_USER") // Add the ROLE_USER role
        saveUser(user)
    }

    fun getUserByUsername(username: String): User? = userRepository.findByUsername(username)

    fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    override fun loadUserByUsername(username: String): UserDetails? = userRepository.findByUsername(username)
        ?.let { CustomUserDetails(it) }

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun saveUser(user: User): User = userRepository.save(user)

    class CustomUserDetails(val user: User): UserDetails{
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> = user.roles.map{SimpleGrantedAuthority(it)}.toMutableSet()

        override fun getPassword(): String = user.password

        override fun getUsername(): String = user.username

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true

        override fun isCredentialsNonExpired(): Boolean = true

        override fun isEnabled(): Boolean = true
    }


}
