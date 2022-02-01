package com.fefuproject.druzhbank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.fefuproject.druzhbank.ui.theme.DruzhbankTheme
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.usecase.GetUserUseCase
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : FragmentActivity() {

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getUserUseCase: GetUserUseCase
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            launchBiometrics(
                true
            )
        }

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preferenceProvider.biometricPreference == PreferenceProvider.STATUS_BIOMETRICS_ENABLED) launchBiometrics(
            false
        )
        runBlocking {
            username = getUserUseCase.invoke(preferenceProvider.token!!)?.name
        }
        setContent {
            DruzhbankTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RootView(
                        preferenceProvider.privateKey == null
                    )
                }
            }
        }
    }

    private fun launchBiometrics(firstTime: Boolean) {
        val biometricManager = BiometricManager.from(this as Context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errorCode == 10) {
                                if (firstTime) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Снова включить биометрию можно в настройках",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    preferenceProvider.updateBiometricPreference(PreferenceProvider.STATUS_BIOMETRICS_DISABLED)
                                    setResult(RESULT_OK)
                                    finish()
                                }

                            }
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            preferenceProvider.updateBiometricPreference(PreferenceProvider.STATUS_BIOMETRICS_ENABLED)
                            setResult(RESULT_OK)
                            finish()
                        }
                    })
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Вход по биометрии")
                    .setSubtitle("Входите по биометрическим данным")
                    .setAllowedAuthenticators(BIOMETRIC_STRONG)
                    .setNegativeButtonText("Войти по паролю")
                    .build()
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                authLauncher.launch(Intent(Settings.ACTION_FINGERPRINT_ENROLL).apply {
                    putExtra(
                        "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED",
                        BIOMETRIC_STRONG
                    )
                })
            }
            else -> {
                preferenceProvider.updateBiometricPreference(PreferenceProvider.STATUS_BIOMETRICS_DISABLED)
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    @Composable
    fun RootView(firstTime: Boolean = false) {
        var input by remember { mutableStateOf("") }
        if (input.length == 5) {
            if (firstTime) {
                preferenceProvider.updatePrivateKey(input)
                Wearable.getDataClient(this)
                    .putDataItem(PutDataRequest.create("/pin").apply { data = input.toByteArray() })
                launchBiometrics(preferenceProvider.biometricPreference == PreferenceProvider.STATUS_BIOMETRICS_NONE)
            } else if (input != preferenceProvider.privateKey) {
                input = ""
                Toast.makeText(this, "Неправильный код!", Toast.LENGTH_SHORT).show()
            } else {
                setResult(RESULT_OK)
                finish()
            }
        }
        DrawDefaultBackground()
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Image(
                modifier = Modifier
                    .scale(3f)
                    .offset(0.dp, 23.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                text = "Здравствуйте, " + username,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            )
            if (firstTime)
                Text(
                    text = "Давайте создадим пин-код",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                )

            BasicTextField(
                value = input,
                onValueChange = { if (it.length <= 5) input = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = { text ->
                    val newText =
                        AnnotatedString("●".repeat(text.length) + "○".repeat(5 - text.length))
                    TransformedText(newText, OffsetMapping.Identity)
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 10.sp
                ),
                cursorBrush = SolidColor(Color.Transparent)
            )
        }
    }
}

@Composable
fun DrawDefaultBackground() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.xiphone),
        contentDescription = "background",
        contentScale = ContentScale.FillBounds
    )
}