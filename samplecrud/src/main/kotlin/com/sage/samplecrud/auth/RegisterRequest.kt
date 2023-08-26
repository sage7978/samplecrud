package com.sage.samplecrud.auth

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor


@NoArgsConstructor
@AllArgsConstructor
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val mobileNumber: String
)
