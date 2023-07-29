package com.sage.samplecrud.exception

class ResourceNotFoundException(message: String? = "Resource not found")
    : RuntimeException(message){
}