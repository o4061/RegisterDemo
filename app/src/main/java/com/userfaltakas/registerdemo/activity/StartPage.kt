package com.userfaltakas.registerdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.userfaltakas.registerdemo.R
import com.userfaltakas.registerdemo.databinding.StartPageBinding
import com.userfaltakas.registerdemo.repository.RegisterRepository

class StartPage : AppCompatActivity() {
    private lateinit var binding: StartPageBinding
    lateinit var viewModel: StartPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = StartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun initViewModel() {
        val registerRepository = RegisterRepository()
        val viewModelFactory = StartPageViewModelProvider(registerRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)
    }
}