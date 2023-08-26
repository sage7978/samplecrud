package com.sage.samplecrud.auth

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthenticationController {

    @Autowired
    lateinit var authenticationService: AuthenticationService

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.register(request))
    }

    @PostMapping("/authenticate")
    fun register(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }
}