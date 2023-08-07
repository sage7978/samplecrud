package com.sage.samplecrud.repo

import com.sage.samplecrud.security.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, Int> {

    fun findByEmail(email : String): User?
}