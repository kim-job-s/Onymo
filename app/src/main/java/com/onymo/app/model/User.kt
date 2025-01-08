package com.onymo.app.model

data class User (
    val name: String,
    val email: String,
    val password: String,
    val companyName: String,
    val department: String,
    val position: String,
    val job: String
)