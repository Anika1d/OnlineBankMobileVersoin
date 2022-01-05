package com.fefuproject.druzhbank.dirprofile

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.dirprofile.dircard.Cards
import com.fefuproject.druzhbank.dirprofile.dircard.CardsAdapter
import com.fefuproject.druzhbank.dirprofile.dircredit.Credits
import com.fefuproject.druzhbank.dirprofile.dircredit.CreditsAdapter
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays
import com.fefuproject.druzhbank.dirprofile.dirpay.PaysAdapter
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
                        makeText(
                            this.applicationContext,
                            "изменить пароль",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.change_login_menu -> {
                        makeText(
                            this.applicationContext,
                            "изменить логин",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.history_locale_menu -> {
                        makeText(
                            this.applicationContext,
                            "История посещений",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.info_from_pr_menu ->
                        makeText(
                            this.applicationContext, "инофрмация о приложении",
                            Toast.LENGTH_SHORT
                        ).show()

                    R.id.out_account_menu ->
                        makeText(
                            this.applicationContext,
                            "Выйти из аккаунта",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                return@setOnMenuItemClickListener true
            }
        }

    }


}