package com.fefuproject.druzhbank.di

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.fefuproject.druzhbank.UI.InputActivity
import com.fefuproject.druzhbank.extensions.InputType
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val DISABLE_AUTH_OBSERVER = true

@ExperimentalWearMaterialApi
@ActivityScoped
class AuthStateObserver @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    @ActivityContext private val context: Context
) : DefaultLifecycleObserver {

    private val activity = context as ComponentActivity
    private var _verificationState = MutableStateFlow<Boolean?>(null)
    val verificationState get() = _verificationState.asStateFlow()
    private lateinit var authLauncher: ActivityResultLauncher<Intent>
    private val phoneCheckerContext = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var phoneCheckerJob: Job? = null

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
                if (result.data?.getStringExtra("PIN") == preferenceProvider.PIN) {
                    preferenceProvider.updateLastVerifiedTime()
                    _verificationState.value = true
                }
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (DISABLE_AUTH_OBSERVER) {
            _verificationState.value = true
            return
        } else {
            val diff =
                (System.currentTimeMillis() - preferenceProvider.lastVerifiedTimestamp) / 1000 // 60000 - minutes
            if (diff > 30) { // 60 might be better
                if (preferenceProvider.PIN == null) {
                    if (phoneCheckerJob == null)
                        phoneCheckerJob = phoneCheckerContext.launch {
                            var checkDone: Boolean
                            while (_verificationState.value != true) {
                                checkDone = false
                                Wearable.getNodeClient(activity).connectedNodes.addOnSuccessListener {
                                    if (it.isNotEmpty())
                                        _verificationState.value = it.get(0)?.isNearby ?: false
                                    else
                                        _verificationState.value = false
                                    checkDone = true
                                }
                                while (!checkDone) {
                                    delay(2000)
                                }
                            }
                        }
                } else {
                    _verificationState.value = false
                    val intent = Intent(activity, InputActivity::class.java)
                    intent.putExtra("type", InputType.Password)
                    authLauncher.launch(intent)
                }
            } else {
                _verificationState.value = true
            }
        }
    }
}