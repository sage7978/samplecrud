package com.sage.samplecrud.auth

import com.sage.samplecrud.config.JwtService
import com.sage.samplecrud.exception.ResourceNotFoundException
import com.sage.samplecrud.repo.UserRepository
import com.sage.samplecrud.security.Role
import com.sage.samplecrud.security.User
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AuthenticationService {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtService: JwtService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager


    fun register(request: RegisterRequest): AuthenticationResponse? {
        val user = createUser(request)
        userRepository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user: User = userRepository.findByEmail(request.email) ?: throw ResourceNotFoundException()
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }

    fun createUser(request: RegisterRequest): User {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            pass = passwordEncoder.encode(request.password),
            mobileNumber = request.mobileNumber,
            role = Role.USER
        )
        return user
    }
}