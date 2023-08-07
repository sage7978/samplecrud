package com.sage.samplecrud.config

import com.sage.samplecrud.repo.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@RequiredArgsConstructor
class ApplicationConfig {

    @Autowired
    lateinit var userRepository: UserRepository

    @Bean
    fun userDetailsService(): UserDetailsService {
    }
}