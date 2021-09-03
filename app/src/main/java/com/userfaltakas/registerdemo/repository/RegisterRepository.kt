package com.userfaltakas.registerdemo.repository

import com.userfaltakas.registerdemo.api.RetrofitInstance
import com.userfaltakas.registerdemo.data.Credential
import com.userfaltakas.registerdemo.data.RegistrationInfo
import com.userfaltakas.registerdemo.data.User
import retrofit2.Response

class RegisterRepository {
    suspend fun getRegisterUser(user_id: String): Response<User> {
        return RetrofitInstance.api.getRegisterUser(user_id)
    }

    suspend fun addNewUser(registrationInfo: RegistrationInfo): Response<Credential> {
        return RetrofitInstance.api.createUser(registrationInfo)
    }
}