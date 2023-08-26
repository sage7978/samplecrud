package com.sage.samplecrud.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Objects

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter: OncePerRequestFilter() {

    @Autowired
    lateinit var jwtService: JwtService

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization");
        val jwt: String
        val userEmail: String
        if(authHeader == null || (authHeader != null && !authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return
        }
        jwt = authHeader.substring(7)
        userEmail = jwtService.extractUserName(jwt)
        if(SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)
             if(jwtService.isTokenValid(jwt, userDetails)) {
                 val authToken = UsernamePasswordAuthenticationToken(
                     userDetails,
                     null,
                     userDetails.authorities
                 )
                 authToken.details = WebAuthenticationDetailsSource().
                         buildDetails(request)
                 SecurityContextHolder.getContext().authentication = authToken
             }
        }
        filterChain.doFilter(request, response)
    }
}