package com.WTAK.WebTerm

data class User(
    val id: String,
    val username: String,
    val email: String,
    val isBanned: Boolean,
    val isAdmin: Boolean,
    val role: String? = null
)
