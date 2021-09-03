package com.userfaltakas.registerdemo.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.registerdemo.api.Resource
import com.userfaltakas.registerdemo.data.Credential
import com.userfaltakas.registerdemo.data.RegistrationInfo
import com.userfaltakas.registerdemo.data.User
import com.userfaltakas.registerdemo.repository.RegisterRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel(private val repository: RegisterRepository) : ViewModel() {
    val user: MutableLiveData<Resource<User>> = MutableLiveData()
    val credential: MutableLiveData<Resource<Credential>> = MutableLiveData()

    fun getRegisterUser(user_id: String) = viewModelScope.launch {
        val response = repository.getRegisterUser(user_id)
        user.postValue(handleUserResponse(response))
    }

    fun addNewUser(registrationInfo: RegistrationInfo) = viewModelScope.launch {
        val response = repository.addNewUser(registrationInfo)
        credential.postValue(handleCredentialResponse(response))
    }

    private fun handleUserResponse(response: Response<User>): Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleCredentialResponse(response: Response<Credential>): Resource<Credential> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(response.message())
    }
}