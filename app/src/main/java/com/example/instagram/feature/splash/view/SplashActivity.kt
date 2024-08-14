package com.example.instagram.feature.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.App
import com.example.instagram.common.extension.animationEnd
import com.example.instagram.databinding.ActivitySplashBinding
import com.example.instagram.feature.di.component.SplashComponent
import com.example.instagram.feature.login.view.LoginActivity
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.splash.presentation.SplashViewModel
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<SplashViewModel> { viewModelFactory }

    lateinit var splashComponent: SplashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashComponent = (applicationContext as App).applicationComponent.splashComponent().create()
        splashComponent.inject(this)

        binding.splashImg.animate().apply {
            setListener(animationEnd {
                viewModel.authenticated()
            })
            duration = 1000
            alpha(1.0f)
            start()
        }

        viewModel.isAuthenticated.observe(this) { isAuth ->
            if (isAuth) animationEndByClass(MainActivity::class.java)
        }

        viewModel.isFailure.observe(this) { isFailure ->
            if(isFailure) animationEndByClass(LoginActivity::class.java)
        }

    }

    private fun animationEndByClass(cls: Class<*>){
        val animator = binding.splashImg.animate()
        val intent = Intent(this,cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        setAnimationEnd(animator,intent)
    }

    private fun setAnimationEnd(animator: ViewPropertyAnimator, intent: Intent){
        animator.apply {
            setListener(animationEnd {
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            })
            startDelay = 1000
            duration = 1000
            alpha(0.0f)
            start()
        }
    }

}