package com.fefuproject.druzhbank.registerScreen

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.fefuproject.druzhbank.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        cliker_rules()
        viewModel.initData(binding, this)
        setContentView(binding.root)
    }

    private fun cliker_rules() {
        val text_Rules = binding.agreeRul.text
        val s_r = SpannableString(text_Rules);
        val clickableSpanCon = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@RegisterActivity, "Config", Toast.LENGTH_LONG).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false)
            }
        }
        val clickableSpanRules = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@RegisterActivity, "Rules", Toast.LENGTH_LONG).show()

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.setColor(Color.BLACK)
            }
        }
        s_r.setSpan(clickableSpanCon, 42, 70, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s_r.setSpan(clickableSpanRules, 125, 152, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.agreeRul.movementMethod = LinkMovementMethod.getInstance()
        binding.agreeRul.text = s_r
    }



}