package com.example.instagram.splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewPropertyAnimator
import com.example.instagram.di.DependencyInjector
import com.example.instagram.common.extension.animationEnd
import com.example.instagram.databinding.ActivitySplashBinding
import com.example.instagram.login.view.LoginActivity
import com.example.instagram.main.view.MainActivity
import com.example.instagram.splash.SplashContract
import com.example.instagram.splash.presentation.SplashPresenter

class SplashActivity: AppCompatActivity(), SplashContract.View {

    override lateinit var presenter: SplashContract.Presenter
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = DependencyInjector.splashRepository()
        presenter = SplashPresenter(this,repository)

        binding.splashImg.animate().apply {
            setListener(animationEnd {
                presenter.authenticated()
            })
            duration = 1000
            alpha(1.0f)
            start()
        }
    }

    override fun goToMainScreen() {
        animationEndByClass(MainActivity::class.java)
    }

    override fun goToLoginScreen() {
        animationEndByClass(LoginActivity::class.java)
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}