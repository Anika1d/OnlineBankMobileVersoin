package com.fefuproject.druzhbank.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fefuproject.druzhbank.dirbank.BankActivity
import com.fefuproject.druzhbank.dirprofile.ProfileActivity
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.dirvalute.ValuteActivity
import com.fefuproject.druzhbank.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val AUTH_ON =false;

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.includeButtonBunk.conBank.setOnClickListener {
            val intentt = Intent(this, BankActivity::class.java) //переход на другую активити
            startActivity(intentt)
        }
        binding.includeButtonValute.conValute.setOnClickListener {
            val intentt = Intent(this, ValuteActivity::class.java)
            startActivity(intentt)
        }
        binding.button.setOnClickListener { //вызов диалога авторизации
            AlertDialogAuthorize()
        }
        val currentDate = Date() ///заполнение изменяемых данных
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)
        binding.includeButtonValute.date.text = dateText.toString()
        binding.includeButtonValute.eurText.text = "EUR 166,5" ///ТАКЖЕ ИЗ БД
        binding.includeButtonValute.usdText.text = "USD 70,1"
        setContentView(binding.root)
    }

    private fun AlertDialogAuthorize() { //создание диалога авторизации
        val builder = AlertDialog.Builder(this)
        val alert = builder.create();
        //   builder.setNegativeButton("Отмена"){dialog,i->
        val promptsView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_authorize, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val bun_auth = promptsView.findViewById<MaterialButton>(R.id.auth_button)
        if (AUTH_ON)
            bun_auth.setOnClickListener {
                viewModel.login(
                    promptsView.findViewById<TextInputEditText>(R.id.login_edit).text.toString(),
                    promptsView.findViewById<TextInputEditText>(R.id.password_edit).text.toString(),
                    this
                ) { startActivity(Intent(this, ProfileActivity::class.java)) }
            }
        else startActivity(Intent(this, ProfileActivity::class.java))

        alert.show()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}