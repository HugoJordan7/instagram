package com.example.instagram.feature.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.di.DependencyInjector
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

    override fun goToNameAndPasswordScreen(email: String) {
        attachListener.goToNameAndPasswordScreen(email)
    }

    override fun onEmailFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
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