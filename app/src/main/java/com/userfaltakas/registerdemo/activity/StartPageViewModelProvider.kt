package com.userfaltakas.registerdemo.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.userfaltakas.registerdemo.repository.RegisterRepository

class StartPageViewModelProvider(private val repository: RegisterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartPageViewModel(repository) as T
    }
}
