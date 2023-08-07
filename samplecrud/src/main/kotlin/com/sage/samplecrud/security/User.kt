package com.sage.samplecrud.security

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")
data class User (

    @Id
    @GeneratedValue
    val id: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var pass: String,
    var mobileNumber: String,
    @Enumerated(EnumType.STRING)
    var role: Role
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = mutableListOf()
        val userAuthority = SimpleGrantedAuthority(Role.USER.name)
        authorities.add(userAuthority)
        return authorities
    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}