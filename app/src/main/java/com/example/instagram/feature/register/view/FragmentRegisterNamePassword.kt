package com.example.instagram.feature.register.view

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragmentMVVM
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.example.instagram.feature.register.presentation.RegisterNamePasswordViewModel
import javax.inject.Inject

class FragmentRegisterNamePassword: BaseFragmentMVVM<FragmentRegisterNamePasswordBinding, RegisterNamePasswordViewModel>(
    R.layout.fragment_register_name_password,
    FragmentRegisterNamePasswordBinding::bind
){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel by viewModels<RegisterNamePasswordViewModel> { viewModelFactory }

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    private var attachListener: FragmentAttachListener? = null

    override fun setupViews() {
        val email = arguments?.getString(KEY_EMAIL) ?: throw Exception("Email not found")

        viewModel.isEmailSuccess.observe(this){ name ->
            name?.let { onRegisterSuccess(it) }
        }

        viewModel.isPasswordFailure.observe(this){ isPasswordFailure ->
            displayPasswordFailure(isPasswordFailure)
        }

        viewModel.isNameFailure.observe(this){ isNameFailure ->
            displayNameFailure(isNameFailure)
        }

        viewModel.isFailure.observe(this){ message ->
            message?.let { onRegisterFailure(it) }
        }

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        binding?.apply {
            registerEditName.addTextChangedListener(watcher)
            registerEditPassword.addTextChangedListener(watcher)
            registerEditPasswordConfirm.addTextChangedListener(watcher)
            registerBtnNamePasswordNext.setOnClickListener {
                viewModel.register(
                    email,
                    registerEditName.text.toString(),
                    registerEditPassword.text.toString(),
                    registerEditPasswordConfirm.text.toString()
                )
            }
        }
    }

    private val watcher = CustomTextWatcher {
        binding?.apply {
            val name = registerEditName.text.toString()
            val password = registerEditPassword.text.toString()
            val confirm = registerEditPasswordConfirm.text.toString()
            registerBtnNamePasswordNext.isEnabled = name.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()
            if(name == it){
                displayNameFailure(null)
            } else{
                displayPasswordFailure(null)
            }
        }
    }

    private fun showProgress(enabled: Boolean) {
        binding?.registerBtnNamePasswordNext?.showProgress(enabled)
    }

    private fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameLayout?.error = nameError?.let { getString(it) }
    }

    private fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordLayout?.error = passwordError?.let { getString(it) }
    }

    private fun onRegisterFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun onRegisterSuccess(name: String) {
        attachListener?.goToWelcomeScreen(name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListener){
            attachListener = context
        }
        if(context is RegisterActivity){
            context.registerComponent.inject(this)
        }
    }

}