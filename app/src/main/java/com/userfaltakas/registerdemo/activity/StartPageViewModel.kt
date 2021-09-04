package com.userfaltakas.registerdemo.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.userfaltakas.registerdemo.api.Resource
import com.userfaltakas.registerdemo.data.ApiError
import com.userfaltakas.registerdemo.data.Credential
import com.userfaltakas.registerdemo.data.RegistrationInfo
import com.userfaltakas.registerdemo.data.User
import com.userfaltakas.registerdemo.repository.RegisterRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class StartPageViewModel(private val repository: RegisterRepository) : ViewModel() {

    var user: MutableLiveData<Resource<User>>
    lateinit var credential: MutableLiveData<Resource<Credential>>

    init {
        user = MutableLiveData()
    }

    fun getRegisterUser(user_id: String) = viewModelScope.launch {
        user = MutableLiveData()
        val response = repository.getRegisterUser(user_id)
        user.postValue(handleUserResponse(response))
    }

    fun addNewUser(registrationInfo: RegistrationInfo) = viewModelScope.launch {
        credential = MutableLiveData()
        val response = repository.addNewUser(registrationInfo)
        credential.postValue(handleCredentialResponse(response))
    }

    private fun handleUserResponse(response: Response<User>): Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(getErrorMsg(response.errorBody()!!))
    }

    private fun handleCredentialResponse(response: Response<Credential>): Resource<Credential> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(getErrorMsg(response.errorBody()!!))
    }

    private fun getErrorMsg(response: ResponseBody): String {
        val gson = Gson()
        val apiError: ApiError? =
            gson.fromJson(response.charStream(), ApiError::class.java)
        if (apiError != null) {
            return apiError.error
        }
        return ""
    }
}