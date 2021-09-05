package com.userfaltakas.registerdemo.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.userfaltakas.registerdemo.R
import com.userfaltakas.registerdemo.activity.StartActivity
import com.userfaltakas.registerdemo.activity.StartActivityViewModel
import com.userfaltakas.registerdemo.api.Resource
import com.userfaltakas.registerdemo.data.RegistrationInfo
import com.userfaltakas.registerdemo.databinding.CustomeDialogBinding
import com.userfaltakas.registerdemo.databinding.FragmentProfileBinding
import com.userfaltakas.registerdemo.network.NetworkManager

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialogBinding: CustomeDialogBinding
    private lateinit var dialog: Dialog
    private var network = NetworkManager()
    private lateinit var viewModel: StartActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as StartActivity).viewModel
        registrationForm()
    }

    private fun createUser(registrationInfo: RegistrationInfo) {
        if (network.checkNetworkAvailability(requireContext())) {
            viewModel.addNewUser(registrationInfo)
            viewModel.credential.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context,
                            getString(R.string.successful_registration),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        viewModel.getRegisterUser(response.data?.id.toString())
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registrationForm() {
        binding.registerBtn.setOnClickListener {
            setCustomDialog()
            dialogBinding.applyBtn.setOnClickListener {
                val email = dialogBinding.emailTextInput.editText?.text.toString()
                val password = dialogBinding.passwordTextInput.editText?.text.toString()
                val registrationInfo = RegistrationInfo(email, password)
                dialog.dismiss()
                createUser(registrationInfo)
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