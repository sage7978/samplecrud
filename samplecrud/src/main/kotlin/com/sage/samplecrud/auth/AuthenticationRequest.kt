package com.sage.samplecrud.auth

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
data class AuthenticationRequest(
    val email: String,
    val password: String
)
