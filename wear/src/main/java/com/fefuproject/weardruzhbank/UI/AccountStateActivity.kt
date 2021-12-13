package com.fefuproject.weardruzhbank.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text

@ExperimentalWearMaterialApi
class AccountStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }


    override fun onStart() {
        super.onStart()
        val contract = ActivityResultContracts.StartActivityForResult()
        contract.createIntent(this, Intent(this, PasswordGuardActivity::class.java))
        val launcher = registerForActivityResult(contract) { result ->
            println("у меня есть результат!")
        }
        launcher.launch(Intent(this, PasswordGuardActivity::class.java))
    }

    @Composable
    fun RootView() {
        Text(text = "я аккаунтстейт ")
    }
}