package com.WTAK.WebTerm

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminApi {
    @POST("/admin/ban")
    fun banUser(@Body request: BanRequest): Call<AdminResponse>

    @POST("/admin/unban")
    fun unbanUser(@Body request: UnbanRequest): Call<AdminResponse>

    @POST("/admin/become")
    fun becomeAdmin(@Body request: BecomeAdminRequest): Call<AdminResponse>

    @GET("/admin/users")
    fun getUsers(): Call<List<User>>
}
