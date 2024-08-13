package com.example.instagram.feature.add.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.instagram.R
import com.example.instagram.common.util.PHOTO_URI
import com.example.instagram.databinding.ActivityAddBinding
import com.example.instagram.feature.add.presentation.AddViewModel

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var uri: Uri

    private lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addToolbar)

        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        supportActionBar?.setHomeAsUpIndicator(drawable)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        uri = intent.extras?.getParcelable(PHOTO_URI) ?: throw RuntimeException("photo not found")

        binding.addImgCaption.setImageURI(uri)

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.isFailure.observe(this){ isFailure ->
            isFailure?.let { displayFailure(it) }
        }

        viewModel.isSuccess.observe(this){ isSuccess ->
            if (isSuccess) displaySuccess()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_share -> {
                viewModel.createPost(uri, binding.addEditCaption.text.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgress(enabled: Boolean) {
        binding.addProgress.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    private fun displaySuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun displayFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}