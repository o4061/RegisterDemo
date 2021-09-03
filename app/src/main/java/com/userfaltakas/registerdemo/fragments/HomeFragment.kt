package com.userfaltakas.registerdemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.userfaltakas.registerdemo.activity.StartPage
import com.userfaltakas.registerdemo.activity.StartPageViewModel
import com.userfaltakas.registerdemo.api.Resource
import com.userfaltakas.registerdemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StartPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as StartPage).viewModel

        setContext()
    }

    private fun setContext() {
        viewModel.user.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.apply {
                        profileCard.visibility = View.VISIBLE
                        welcomeTextView.visibility = View.INVISIBLE
                        firstNameTextView.text = response.data?.data?.first_name
                        lastNameTextView.text = response.data?.data?.last_name
                        emailNameTextView.text = response.data?.data?.email
                        Glide.with(this@HomeFragment).load(response.data?.data?.avatar)
                            .into(binding.avatarImage)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}