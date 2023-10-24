package com.example.diplombackend.entitys

import com.example.diplombackend.sotial.SocialNetwork
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var username: String,
    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    var email: String,
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$",
        message = "Password must be at least 8 characters long and contain at least one letter and one number"
    )
    var password: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private val tokens: MutableSet<Token> = mutableSetOf(),
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
     var roles: MutableSet<String> = HashSet()
) { constructor(user: User) : this(user.id, user.username, user.email, user.password, user.tokens, user.roles)

    companion object{
        @JvmStatic
         fun User.addToken(token: Token) {
            tokens.add(token)
            token.user = this
        }
        @JvmStatic
         fun User.changeToken(token: Token) {
             removeToken(token)
            tokens.add(token)
            token.user = this

        }
        @JvmStatic
         fun User.removeToken(token: Token) {
            tokens.remove(token)
        }
        @JvmStatic
         fun User.getToken(socialNetwork: SocialNetwork):String? = tokens.find { it.socialNetwork == socialNetwork }?.token
         fun User.socialId(socialNetwork: SocialNetwork):Long? = tokens.find { it.socialNetwork == socialNetwork }?.id

    }

    override fun equals(other: Any?): Boolean {
        if( other !is User) return false
        if (this === other) return true
        return username == other.username
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }
}

