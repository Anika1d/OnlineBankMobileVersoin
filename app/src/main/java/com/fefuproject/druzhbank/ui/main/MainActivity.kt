package com.fefuproject.druzhbank.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fefuproject.druzhbank.bank.BankActivity
import com.fefuproject.druzhbank.dirprofile.ProfileActivity
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.valute.ValuteActivity
import com.fefuproject.druzhbank.databinding.ActivityMainBinding
import com.fefuproject.shared.account.domain.models.ValuteModel
import com.fefuproject.shared.account.domain.models.ValuteResponseModel
import com.fefuproject.shared.account.domain.usecase.GetValuteUseCase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

const val AUTH_ON =true;

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.includeButtonValute.shimmerText.startShimmer()
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
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)

        viewModel.checkValute(binding)
        binding.includeButtonValute.date.text = dateText.toString()

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
        if (AUTH_ON){
                bun_auth.setOnClickListener {
                    viewModel.login(
                        promptsView.findViewById<TextInputEditText>(R.id.login_edit).text.toString(),
                        promptsView.findViewById<TextInputEditText>(R.id.password_edit).text.toString(),
                        this
                    ) { startActivity(Intent(this, ProfileActivity::class.java)) }
                    alert.dismiss()
                }

        }
        else startActivity(Intent(this, ProfileActivity::class.java))
        if(viewModel.preferenceProvider.token == null) alert.show()
        else startActivity(Intent(this, ProfileActivity::class.java))
    }
}