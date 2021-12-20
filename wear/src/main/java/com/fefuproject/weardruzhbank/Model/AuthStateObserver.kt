package com.fefuproject.weardruzhbank.Model

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.fefuproject.weardruzhbank.UI.PasswordGuardActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

const val DISABLE_AUTH_OBSERVER = true

@ExperimentalWearMaterialApi
@ActivityScoped
class AuthStateObserver @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    @ActivityContext private val context: Context
) : DefaultLifecycleObserver {

    private val activity = context as ComponentActivity
    var verificationState: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var authLauncher: ActivityResultLauncher<Intent>

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val contract = ActivityResultContracts.StartActivityForResult()
        authLauncher = activity.registerForActivityResult(contract) { result ->
            if (result.resultCode != ComponentActivity.RESULT_OK) {
                Toast.makeText(activity, "Не удалось войти...", Toast.LENGTH_SHORT).show()
                activity.finish()
            } else {
                preferenceProvider.updateLastVerifiedTime()
                verificationState.value = true
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (DISABLE_AUTH_OBSERVER){
            verificationState.value = true
            return
        }
        val diff =
            (System.currentTimeMillis() - preferenceProvider.lastVerifiedTimestamp) / 1000 // 60000 - minutes
        if (diff > 30) { // 60 might be better
            verificationState.value = false
            authLauncher.launch(Intent(activity, PasswordGuardActivity::class.java))
        } else {
            verificationState.value = true
        }
    }
}