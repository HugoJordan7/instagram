package com.example.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context): Dialog(context) {

    private lateinit var binding: DialogCustomBinding
    private lateinit var textButtons: Array<TextView>
    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }

    fun addButton(vararg texts: Int, listener: View.OnClickListener){
        textButtons = Array(texts.size){
            TextView(context)
        }
        texts.forEachIndexed { i, stringId ->
            textButtons[i].apply {
                id = stringId
                setText(stringId)
                setOnClickListener {
                    listener.onClick(it)
                    dismiss()
                }
            }
        }
    }

    override fun show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.show()
        for (textView in textButtons){
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val layoutParams = LinearLayout.LayoutParams(width,height)
            layoutParams.setMargins(30,50,30,50)
            binding.dialogContainer.addView(textView,layoutParams)
        }
        titleId?.let {
            binding.dialogTitle.setText(it)
        }
    }
}