package com.sage.samplecrud.config

import com.sage.samplecrud.repo.UserRepository
import com.sage.samplecrud.security.User
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
@RequiredArgsConstructor
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? =  userRepository.findByEmail(username)
        if(user != null) {
            return user
        }
        throw UsernameNotFoundException("User not Found!!")
    }
}