package com.WTAK.WebTerm

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AdminActions {

    fun banUser(userId: Int, onResult: (Boolean, String) -> Unit) {
        val call = RetrofitClient.adminApi.banUser(BanRequest(userId))
        call.enqueue(object : Callback<AdminResponse> {
            override fun onResponse(call: Call<AdminResponse>, response: Response<AdminResponse>) {
                if (response.isSuccessful) {
                    onResult(true, response.body()?.message ?: "User banned")
                } else {
                    onResult(false, "Failed to ban user")
                }
            }

            override fun onFailure(call: Call<AdminResponse>, t: Throwable) {
                Log.e("AdminActions", "banUser error", t)
                onResult(false, "Error: ${t.message}")
            }
        })
    }

    fun unbanUser(userId: Int, onResult: (Boolean, String) -> Unit) {
        val call = RetrofitClient.adminApi.unbanUser(UnbanRequest(userId))
        call.enqueue(object : Callback<AdminResponse> {
            override fun onResponse(call: Call<AdminResponse>, response: Response<AdminResponse>) {
                if (response.isSuccessful) {
                    onResult(true, response.body()?.message ?: "User unbanned")
                } else {
                    onResult(false, "Failed to unban user")
                }
            }

            override fun onFailure(call: Call<AdminResponse>, t: Throwable) {
                Log.e("AdminActions", "unbanUser error", t)
                onResult(false, "Error: ${t.message}")
            }
        })
    }
}
