package com.example.winterwonderlandapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ShoppingAPI {
    @GET("/products/category/{path}")
    fun getGiftIdeas(@Path("path")  category: String?): Call<ArrayList<PresentObject>>


}