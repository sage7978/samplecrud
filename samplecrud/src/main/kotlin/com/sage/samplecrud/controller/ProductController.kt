package com.sage.samplecrud.controller

import com.sage.samplecrud.entity.Product
import com.sage.samplecrud.exception.ResourceNotFoundException
import com.sage.samplecrud.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/products")
class ProductController {
    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @Autowired
    lateinit var productService: ProductService

    @GetMapping
    fun getAllProducts(@RequestParam consumerKey: String): ResponseEntity<List<Product>> {
        logger.info("Consumer: {}", consumerKey)
        val allProducts = productService.findAll();
        return ResponseEntity(allProducts, HttpStatus.OK)
    }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable(value = "productId") productId: Long): ResponseEntity<Product> {
        val product = productService.findById(productId).orElse(null)
        return if(product != null){
            logger.debug("Retrieved product with ID: $productId")
            ResponseEntity(product, HttpStatus.OK)
        } else {
            logger.warn("Product with ID: $productId not found")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createProduct(@RequestBody product:Product): ResponseEntity<Product> {
        val createdProduct = productService.save(product)
        logger.info("Created product with ID: ${createdProduct.id}")
        return ResponseEntity(createdProduct, HttpStatus.OK)
    }

    @PutMapping("/products/{productId}")
    fun updateProduct(@PathVariable(value = "productId") productId: Long, @RequestBody product: Product): ResponseEntity<Product>? {
        return productService.findById(productId).map { p: Product ->
            p.name = product.name
            p.price = product.price
            productService.save(p)
            logger.info("Updated product with ID: $productId")
            ResponseEntity(p, HttpStatus.OK)
        }.orElseThrow {
            ResourceNotFoundException(
                "productId $productId not found"
            )
        }
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable(value = "productId") productId: Long): ResponseEntity<Unit> {
        val success = productService.deleteById(productId)
        return if (success) {
            logger.info("Deleted person with ID: $productId")
            ResponseEntity(HttpStatus.OK)
        } else {
            logger.warn("Person with ID: $productId not found, delete failed")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}