package com.fefuproject.druzhbank.dirprofile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.historyallpayment.AllHistoryPaymentFragment
import com.fefuproject.druzhbank.mainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.dirprofile.fragmentprofile.HistoryOpenApplicationFragment
import com.fefuproject.druzhbank.dirprofile.fragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.dirprofile.fragmentprofile.RulesFragment
import com.fefuproject.druzhbank.ui.main.MainActivity
import com.fefuproject.shared.account.domain.models.UserModel
import com.fefuproject.shared.account.domain.usecase.ChangePasswordUseCase
import com.fefuproject.shared.account.domain.usecase.ChangeUsernameUseCase
import com.fefuproject.shared.account.domain.usecase.GetUserUseCase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver
    lateinit var binding: ActivityProfileBinding

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var changeUsernameUseCase: ChangeUsernameUseCase

    @Inject
    lateinit var changePasswordUseCase : ChangePasswordUseCase

    lateinit var modelUser: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fun makeCurrentFragment(fragment: Fragment, tag: String) {
            supportFragmentManager.beginTransaction().apply {
                val visibleFragment = supportFragmentManager.fragments.firstOrNull { !it.isHidden }
                val currentFragment = supportFragmentManager.findFragmentByTag(tag)
                if (visibleFragment == currentFragment) return@apply
                visibleFragment?.let { hide(it) }
                if (currentFragment != null) {
                    show(currentFragment)
                } else {
                    add(R.id.fragmentContainerViewProfile, fragment, tag)
                }
                commit()
            }
        }
        runBlocking { modelUser = getUserUseCase.invoke(preferenceProvider.token!!)!! }

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
        binding.includeToolbar.namePesone.text = modelUser.name
        binding.btnNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home ->
                    makeCurrentFragment(ProfileMainFragment(), "ProfileMainFragment")
                R.id.pay ->
                    makeCurrentFragment(MainPaymentFragment(), "MainPaymentFragment")
                R.id.history ->
                    makeCurrentFragment(AllHistoryPaymentFragment(), "AllHistoryPaymentFragment")
                R.id.chat ->
                    makeText(this.applicationContext, "чат", Toast.LENGTH_SHORT).show()
            }
            true
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
                        AlertDialogChange("имя")
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
                        preferenceProvider.updateToken(null)
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
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_change)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val textView = promptsView.findViewById<TextView>(R.id.change_your)
        textView.text = textView.text.toString() + tagChange
        val textView2 = promptsView.findViewById<TextView>(R.id.enter_your)
        textView2.text = textView2.text.toString() + tagChange
        if (tagChange != "имя") {
            val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
            editText.hint = "Введите ваш новый $tagChange"
            val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
            inputTextLayout.hint = "Новый $tagChange"

            val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)

            bun_ch_d.setOnClickListener {
                runBlocking {
                    changePasswordUseCase.invoke(
                        token = preferenceProvider.token!!,
                        new_password = editText.text.toString(),
                        old_password = ""
                    )
                }
                makeText(
                    this.applicationContext,
                    "Вы изменили $tagChange",
                    Toast.LENGTH_SHORT
                ).show()
                alert.dismiss()
            }
        }
        else{
            val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
            editText.hint = "Введите ваше новое $tagChange"
            val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
            inputTextLayout.hint = "Новое $tagChange"

            val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)

            bun_ch_d.setOnClickListener {
                runBlocking {
                    changeUsernameUseCase.invoke(
                        token = preferenceProvider.token!!,
                        username = editText.text.toString()
                    )
                }
                makeText(
                    this.applicationContext,
                    "Вы изменили $tagChange",
                    Toast.LENGTH_SHORT
                ).show()
                alert.dismiss()
            }
        }
        alert.show()
    }


}




