package com.example.instagram.feature.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.FragmentRegisterEmailBinding
import com.example.instagram.feature.register.RegisterEmailContract
import com.example.instagram.feature.register.presentation.RegisterEmailPresenter

class FragmentRegisterEmail: Fragment(R.layout.fragment_register_email), RegisterEmailContract.View {

    private var binding: FragmentRegisterEmailBinding? = null
    override lateinit var presenter: RegisterEmailContract.Presenter
    private lateinit var attachListener: FragmentAttachListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterEmailBinding.bind(view)
        val repository = DependencyInjector.registerRepository()
        presenter = RegisterEmailPresenter(this,repository)
        binding?.apply {
            registerEditEmail.addTextChangedListener(watcher)
            registerTxtLogin.setOnClickListener { 
                activity?.finish()
            }
            registerBtnNext.setOnClickListener {
                presenter.register(registerEditEmail.text.toString())
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

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailLayout?.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailLayout?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        attachListener.goToNameAndPasswordScreen(email)
    }

    override fun onAttach(context: Context) {
        if(context is FragmentAttachListener){
            attachListener = context
        }
        super.onAttach(context)
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}