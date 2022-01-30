package com.fefuproject.druzhbank.profile

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.historyallpayment.AllHistoryPaymentFragment
import com.fefuproject.druzhbank.mainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.profile.fragmentprofile.HistoryOpenApplicationFragment
import com.fefuproject.druzhbank.profile.fragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.profile.fragmentprofile.RulesFragment
import com.fefuproject.druzhbank.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    @Inject
    lateinit var authObserver: AuthStateObserver

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    private val viewModel: ProfileViewModel by viewModels()
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

        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel.initData(binding, this)
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
                        viewModel.AlertDialogChange("пароль")
                    }
                    R.id.change_login_menu -> {
                        viewModel.AlertDialogChange("имя")
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

}




