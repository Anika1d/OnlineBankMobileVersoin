package com.fefuproject.weardruzhbank.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text

@ExperimentalWearMaterialApi
class AccountStateActivity : ComponentActivity() {
    private var verified: MutableState<Boolean>? = null
    private lateinit var authLauncher: ActivityResultLauncher<Intent>
    private var recentlyVerified = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contract = ActivityResultContracts.StartActivityForResult()
        authLauncher = registerForActivityResult(contract) { result ->
            if (result.resultCode != RESULT_OK) {
                Toast.makeText(this, "Не удалось войти...", Toast.LENGTH_SHORT).show()
                finish()
            }
            verified?.value = true
            recentlyVerified = true
        }
        setContent {
            RootView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!recentlyVerified) {
            verified?.value = false
            recentlyVerified = true
            authLauncher.launch(Intent(this, PasswordGuardActivity::class.java))
        }
        recentlyVerified = false
    }

    @Composable
    fun RootView() {
        verified = remember { mutableStateOf(false) }
        if (verified!!.value) {
            AccountView()
        } else {
            Text(text = "Ожидание верификации...", textAlign = TextAlign.Center)
        }
    }

    @Composable
    fun AccountView() {
        Text(text = "Верификация пройдена", textAlign = TextAlign.Center)
    }
}