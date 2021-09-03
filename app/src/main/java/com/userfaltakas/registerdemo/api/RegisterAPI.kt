package com.userfaltakas.registerdemo.api

import com.userfaltakas.registerdemo.data.User
import com.userfaltakas.registerdemo.data.Credential
import com.userfaltakas.registerdemo.data.RegistrationInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterAPI {
    @GET("users/{user_id}")
    suspend fun getRegisterUser(
        @Path("user_id") userId: String
    ): Response<User>

    @POST("register")
    suspend fun createUser(
        @Body userCredential: RegistrationInfo
    ): Response<Credential>
}