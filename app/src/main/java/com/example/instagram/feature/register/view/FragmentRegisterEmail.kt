package com.example.instagram.feature.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragmentMVVM
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.FragmentRegisterEmailBinding
import com.example.instagram.feature.register.presentation.RegisterEmailViewModel

class FragmentRegisterEmail: BaseFragmentMVVM<FragmentRegisterEmailBinding, RegisterEmailViewModel>(
    R.layout.fragment_register_email,
    FragmentRegisterEmailBinding::bind
) {

    override lateinit var viewModel: RegisterEmailViewModel

    private lateinit var attachListener: FragmentAttachListener

    override fun setupViews() {

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.isEmailSuccess.observe(this){ email ->
            email?.let { goToNameAndPasswordScreen(it) }
        }

        viewModel.isEmailFailure.observe(this){ message ->
            message?.let { displayEmailFailure(it) }
        }

        binding?.apply {
            registerEditEmail.addTextChangedListener(watcher)
            registerTxtLogin.setOnClickListener {
                activity?.finish()
            }
            registerBtnNext.setOnClickListener {
                viewModel.register(registerEditEmail.text.toString())
            }

            when(resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                Configuration.UI_MODE_NIGHT_YES ->{
                    registerImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO ->{
                    registerImgLogo.imageTintList = ColorStateList.valueOf(Color.BLACK)
                }
            }

        }
    }

    private val watcher = CustomTextWatcher{
        binding?.registerBtnNext?.isEnabled = it.isNotEmpty()
        displayEmailFailure(null)
    }

    private fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    private fun displayEmailFailure(message: String?) {
        binding?.registerEditEmailLayout?.error = message
    }

    private fun goToNameAndPasswordScreen(email: String) {
        attachListener.goToNameAndPasswordScreen(email)
    }

    override fun onAttach(context: Context) {
        if(context is FragmentAttachListener){
            attachListener = context
        }
        super.onAttach(context)
    }

}