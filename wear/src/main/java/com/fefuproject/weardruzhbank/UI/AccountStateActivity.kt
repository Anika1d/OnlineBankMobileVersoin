package com.fefuproject.weardruzhbank.UI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text
import com.fefuproject.weardruzhbank.Model.AuthStateObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class AccountStateActivity: ComponentActivity() {

    @Inject
    lateinit var authObserver: AuthStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }

    @Composable
    fun RootView() {
        val verified = remember { authObserver.verificationState }
        if (verified.value) {
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