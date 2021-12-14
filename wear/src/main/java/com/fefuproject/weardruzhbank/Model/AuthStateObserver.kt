package com.fefuproject.weardruzhbank.Model

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.fefuproject.weardruzhbank.UI.PasswordGuardActivity
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@ExperimentalWearMaterialApi
@Module
@InstallIn(ActivityComponent::class)
class AuthStateObserver @Inject constructor(@ActivityContext private val context: Context): DefaultLifecycleObserver {

    private val activity = context as ComponentActivity
    var verified: MutableState<Boolean>? = null
    private lateinit var authLauncher: ActivityResultLauncher<Intent>
    private var recentlyVerified = false

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
            }
            verified?.value = true
            recentlyVerified = true
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (!recentlyVerified) {
            verified?.value = false
            recentlyVerified = true
            authLauncher.launch(Intent(activity, PasswordGuardActivity::class.java))
        }
        recentlyVerified = false
    }
}