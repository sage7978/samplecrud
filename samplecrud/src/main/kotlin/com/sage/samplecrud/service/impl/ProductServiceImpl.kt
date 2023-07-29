package com.sage.samplecrud.service.impl

import com.sage.samplecrud.controller.ProductController
import com.sage.samplecrud.entity.Product
import com.sage.samplecrud.repo.ProductRepository
import com.sage.samplecrud.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductServiceImpl : ProductService {

    private val logger = LoggerFactory.getLogger(ProductService::class.java)

    @Autowired
    lateinit var productRepo: ProductRepository

    override fun save(product: Product): Product {
        return productRepo.save(product)
    }

    override fun deleteById(id: Long): Boolean {
        val existingProduct = productRepo.findById(id).orElse(null)
        return if(existingProduct != null){
            productRepo.delete(existingProduct)
            true
        }
        else {
            logger.debug("Product with ID: $id couldn't be found")
            false
        }
    }

    override fun findById(id: Long): Optional<Product> {
        return productRepo.findById(id)
    }

    override fun findAll(): List<Product> {
        return productRepo.findAll()
    }
}