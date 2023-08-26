package com.sage.samplecrud.auth

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor


@Builder
@AllArgsConstructor
@NoArgsConstructor
data class AuthenticationResponse(
    val token: String
)
