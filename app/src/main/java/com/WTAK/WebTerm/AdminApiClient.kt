package com.WTAK.WebTerm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AdminApiClient {
    private const val BASE_URL = "https://firebase-0s86.onrender.com/"

    val api: AdminApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdminApi::class.java)
    }
}
