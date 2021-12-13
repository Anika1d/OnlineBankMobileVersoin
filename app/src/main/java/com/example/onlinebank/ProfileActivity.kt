package com.example.onlinebank

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import com.example.onlinebank.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val logout_im=findViewById<ImageButton>(R.id.logout_b_i)
        logout_im.setOnClickListener {
            startActivity(Intent(this, BankActivity::class.java))
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)


    }
}