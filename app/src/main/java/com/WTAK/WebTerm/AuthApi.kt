package com.WTAK.WebTerm

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/register")
    fun register(@Body request: RegisterRequest): Call<ApiResponse>

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<ApiResponse>

    @POST("/auth/adminLogin")
    fun adminLogin(@Body request: AdminLoginRequest): Call<ApiResponse>
}
