package com.fefuproject.druzhbank.UI.settings

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsActivityViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
) : ViewModel() {
    private var _pinEnabled = MutableStateFlow(preferenceProvider.PIN != null)
    val pinEnabled get() = _pinEnabled.asStateFlow()
    private var _currencyEnabled = MutableStateFlow(preferenceProvider.currencyEnabled)
    val currencyEnabled get() = _currencyEnabled.asStateFlow()
    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    fun togglePINEnabled(context: Context) {
        if (isRefreshing.value) return
        if (!_pinEnabled.value) {
            _isRefreshing.value = true
            val dataClient = Wearable.getDataClient(context)
            dataClient.getDataItems(Uri.parse("wear://*/pin")).addOnSuccessListener {
                if (it.count > 0) {
                    preferenceProvider.updatePIN(String(it[0].data, Charsets.UTF_8))
                    _pinEnabled.value = true
                } else {
                    Toast.makeText(context, "Не удалось найти PIN...", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(context, "Не удалось найти PIN...", Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                _isRefreshing.value = false
            }
        } else {
            preferenceProvider.updatePIN(null)
            _pinEnabled.value = false
        }
    }

    fun toggleCurrencyEnabled() {
        _currencyEnabled.value = !_currencyEnabled.value
        preferenceProvider.updateCurrency(currencyEnabled.value)
    }
}