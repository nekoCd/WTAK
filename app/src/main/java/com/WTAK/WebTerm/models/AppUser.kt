package com.WTAK.WebTerm.models

data class AppUser(
    val uid: String = "",
    val email: String = "",
    val role: String = "user", // "user", "tempAdmin", "admin"
    val isPermanent: Boolean = false,
    val expiresAt: Long? = null // for temp admin expiry timestamp
)
