package com.sage.samplecrud.service

import com.sage.samplecrud.entity.Product
import java.util.*

interface ProductService {
    fun save(product: Product): Product
    fun deleteById(id: Long): Boolean
    fun findById(id: Long): Optional<Product>
    fun findAll(): List<Product>
}