package com.example.abhishek_jha_training_project

import retrofit2.http.GET
import retrofit2.Call
interface ApiService {
    @GET("posts")
    fun getData(): Call<List<MyDataItem>>
}