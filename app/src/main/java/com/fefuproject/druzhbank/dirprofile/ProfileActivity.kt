package com.fefuproject.druzhbank.dirprofile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.dirbank.BankActivity
import com.fefuproject.druzhbank.dirprofile.dircard.Cards
import com.fefuproject.druzhbank.dirprofile.dircard.CardsAdapter
import com.fefuproject.druzhbank.dirprofile.dircredit.Credits
import com.fefuproject.druzhbank.dirprofile.dircredit.CreditsAdapter
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.HistoryOpenApplicationFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.RulesFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays
import com.fefuproject.druzhbank.dirprofile.dirpay.PaysAdapter
import com.fefuproject.druzhbank.ui.main.AUTH_ON
import com.fefuproject.druzhbank.ui.main.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver
    lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerViewProfile,
                    ProfileMainFragment(),
                    "ProfileMainFragment"
                )
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerViewProfile, ProfileMainFragment(), "ProfileMainFragment")
                commit()
            }
        }
        binding.includeToolbar.personeBI.setOnClickListener {
            val popupMenu = PopupMenu(this@ProfileActivity.applicationContext, it)
            popupMenu.inflate(R.menu.profile_button_menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.change_password_menu -> {
                        AlertDialogChange("пароль")
                    }
                    R.id.change_login_menu -> {
                        AlertDialogChange("логин")
                    }
                    R.id.history_locale_menu -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.fragmentContainerViewProfile,
                                HistoryOpenApplicationFragment(),
                                "HistoryOpenApplicationFragment"
                            )
                            commit()
                        }
                    }
                    R.id.info_from_pr_menu ->
                        supportFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.fragmentContainerViewProfile,
                                RulesFragment(),
                                "RulesFragment"
                            )
                            commit()
                        }

                    R.id.out_account_menu -> {
                        makeText(
                            this.applicationContext,
                            "Вы вышли из аккаунта",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
                return@setOnMenuItemClickListener true
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun AlertDialogChange(tagChange: String) { //создание диалога авторизации
        val builder = AlertDialog.Builder(this)
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(this).inflate(R.layout.change_private_data_in_user, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val textView = promptsView.findViewById<TextView>(R.id.change_your)
        textView.text = textView.text.toString() + tagChange
        val textView2 = promptsView.findViewById<TextView>(R.id.enter_your)
        textView2.text = textView2.text.toString() + tagChange
        val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
        editText.hint = "Введите ваш новый $tagChange"
        val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
        inputTextLayout.hint = "Новый $tagChange"
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_change)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)

        bun_ch_d.setOnClickListener {
            makeText(
                this.applicationContext,
                "Вы изменили $tagChange",
                Toast.LENGTH_SHORT
            ).show()
            alert.dismiss()
        }
        alert.show()
    }


}




