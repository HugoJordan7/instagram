package com.example.instagram.feature.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.di.DependencyInjector
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.example.instagram.feature.register.RegisterNamePasswordContract
import com.example.instagram.feature.register.presentation.RegisterNamePasswordPresenter

class FragmentRegisterNamePassword :
    Fragment(R.layout.fragment_register_name_password), RegisterNamePasswordContract.View {

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    override lateinit var presenter: RegisterNamePasswordContract.Presenter
    private var attachListener: FragmentAttachListener? = null
    private var binding: FragmentRegisterNamePasswordBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterNamePasswordBinding.bind(view)
        val repository = DependencyInjector.registerRepository()
        presenter = RegisterNamePasswordPresenter(this,repository)
        val email = arguments?.getString(KEY_EMAIL) ?: throw Exception("Email not found")
        binding?.apply {
            registerEditName.addTextChangedListener(watcher)
            registerEditPassword.addTextChangedListener(watcher)
            registerEditPasswordConfirm.addTextChangedListener(watcher)
            registerBtnNamePasswordNext.setOnClickListener {
                presenter.register(
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

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNamePasswordNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameLayout?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordLayout?.error = passwordError?.let { getString(it) }
    }

    override fun onRegisterFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onRegisterSuccess(name: String) {
        attachListener?.goToWelcomeScreen(name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListener){
            attachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

}