package com.example.testapp.repository

import com.example.testapp.models.PostsModel
import com.example.testapp.models.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("users")
    suspend fun getUserInfo(): Response<List<UserModel>>

    @GET("posts")
    suspend fun getUserPost(): Response<List<PostsModel>>
}