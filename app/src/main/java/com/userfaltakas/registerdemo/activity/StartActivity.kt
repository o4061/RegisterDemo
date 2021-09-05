package com.userfaltakas.registerdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.userfaltakas.registerdemo.R
import com.userfaltakas.registerdemo.databinding.StartActivityBinding
import com.userfaltakas.registerdemo.repository.RegisterRepository

class StartActivity : AppCompatActivity() {
    private lateinit var binding: StartActivityBinding
    lateinit var viewModel: StartActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = StartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun initViewModel() {
        val registerRepository = RegisterRepository()
        val viewModelFactory = StartActivityViewModelProvider(registerRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartActivityViewModel::class.java)
    }
}