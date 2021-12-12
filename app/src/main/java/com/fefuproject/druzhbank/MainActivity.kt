package com.fefuproject.druzhbank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.druzhbank.ValuteActivity
import com.fefuproject.druzhbank.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.includeButtonBunk.conBank.setOnClickListener{
            val intentt = Intent(this, BankActivity::class.java) //переход на другую активити
            startActivity(intentt)
        }
        binding.includeButtonValute.conValute.setOnClickListener{

            val intentt = Intent(this, ValuteActivity::class.java)
            startActivity(intentt)
        }
        binding.button.setOnClickListener { //вызов диалога авторизации
            AlertDialogAuthorize()
        }
        val currentDate = Date() ///заполнение изменяемых данных
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)
        binding.includeButtonValute.date.text=dateText.toString()
        binding.includeButtonValute.eurText.text="EUR 166,5" ///ТАКЖЕ ИЗ БД
        binding.includeButtonValute.usdText.text="USD 70,1"
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
        val bun_cancel=promptsView.findViewById<MaterialButton>(R.id.cancel_button)
        bun_cancel.setOnClickListener{
           alert.dismiss()
        }
        alert.show()
    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
        }
}