package com.littlelemon.littlelemonmenu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("/login") // Replace with your endpoint
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<String>
    @GET("/allBooks")
    fun getAllBooks(): Call<List<Book>>

}
