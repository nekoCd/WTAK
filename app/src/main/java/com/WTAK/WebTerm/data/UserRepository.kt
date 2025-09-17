package com.WTAK.WebTerm.data

import com.WTAK.WebTerm.User
import com.WTAK.WebTerm.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun getUsers(onResult: (List<User>) -> Unit) {
        val call = RetrofitClient.adminApi.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    onResult(response.body() ?: emptyList())
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                onResult(emptyList())
            }
        })
    }
}
