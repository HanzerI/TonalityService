package com.example.diplombackend.entitys

import jakarta.persistence.*
import com.example.diplombackend.sotial.SocialNetwork

@Entity
@Table(name = "tokens")
class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val token: String,
    val userTokenId: Long,
    @ManyToOne(fetch = FetchType.LAZY) // Many tokens can belong to one user
    @JoinColumn(name = "user_id") // Foreign key to user
    var user: User? = null,

    @Enumerated(EnumType.STRING)
    var socialNetwork: SocialNetwork

) {
    override fun equals(other: Any?): Boolean {
        if (other !is Token) return  false
        if (this === other) return true
        return socialNetwork == other.socialNetwork
    }

    override fun hashCode(): Int {
        return socialNetwork.hashCode()
    }
}