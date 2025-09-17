package com.WTAK.WebTerm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AdminService {
    val api: AdminApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://firebase-0s86.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdminApi::class.java)
    }
}
