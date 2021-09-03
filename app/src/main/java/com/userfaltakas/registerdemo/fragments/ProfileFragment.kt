package com.userfaltakas.registerdemo.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.userfaltakas.registerdemo.R
import com.userfaltakas.registerdemo.activity.StartPage
import com.userfaltakas.registerdemo.activity.StartPageViewModel
import com.userfaltakas.registerdemo.api.Resource
import com.userfaltakas.registerdemo.data.RegistrationInfo
import com.userfaltakas.registerdemo.databinding.CustomeDialogBinding
import com.userfaltakas.registerdemo.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialogBinding: CustomeDialogBinding
    private lateinit var dialog: Dialog

    private lateinit var viewModel: StartPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as StartPage).viewModel
        registrationForm()

    }

    private fun createUser(registrationInfo: RegistrationInfo) {
        viewModel.addNewUser(registrationInfo)
        viewModel.credential.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    Toast.makeText(context, "Successful registration", Toast.LENGTH_SHORT).show()
                    viewModel.getRegisterUser(response.data?.id.toString())
                }

                is Resource.Error -> {
                    Log.d("error!", response.message.toString())
                }
            }
        })
    }

    private fun registrationForm() {
        binding.registerBtn.setOnClickListener {

            setCustomDialog()

            dialogBinding.applyBtn.setOnClickListener {
                val email = dialogBinding.emailTextInput.editText?.text.toString()
                val password = dialogBinding.passwordTextInput.editText?.text.toString()

                val registrationInfo = RegistrationInfo(email, password)
                createUser(registrationInfo)
                dialog.dismiss()

            }
        }
    }

    private fun setCustomDialog() {
        dialog = Dialog(requireContext())
        dialog.apply {
            setContentView(R.layout.custom_dialog)
            setCancelable(true)
            window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
        }

        dialogBinding = CustomeDialogBinding.inflate(
            LayoutInflater.from(context)
        )

        dialog.setContentView(dialogBinding.root)
    }
}