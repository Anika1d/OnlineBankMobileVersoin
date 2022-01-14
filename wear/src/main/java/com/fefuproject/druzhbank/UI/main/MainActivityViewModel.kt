package com.fefuproject.druzhbank.UI.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.usecase.GetUserUseCase
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.ValuteModel
import com.fefuproject.shared.account.domain.usecase.GetValuteUseCase
import com.google.android.gms.wearable.DataClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getValuteUseCase: GetValuteUseCase,
    private val preferenceProvider: PreferenceProvider
) : ViewModel() {
    private val _username = MutableStateFlow<String?>(null)
    val username get() = _username.asStateFlow()
    private val _tokenExists = MutableStateFlow(false)
    val tokenExists get() = _tokenExists.asStateFlow()
    private val _currencyData = MutableStateFlow<List<ValuteModel>?>(null)
    val currencyData get() = _currencyData.asStateFlow()
    private val _currencyEnabled = MutableStateFlow(preferenceProvider.currencyEnabled)
    val currencyEnabled get() = _currencyEnabled.asStateFlow()

    init {
        refreshUserData()
    }

    private fun refreshUserData() {
        viewModelScope.launch {
            _tokenExists.value = preferenceProvider.token != null
            if (tokenExists.value) {
                val user = getUserUseCase.invoke(preferenceProvider.token!!)
                if (user == null) {
                    preferenceProvider.updateToken(null)
                    _tokenExists.value = false
                } else {
                    _username.value = user.name
                    _currencyData.value =
                        getValuteUseCase.invoke().ValCurs.Valute.filter { valuteModel ->
                            valuteModel.CharCode == "USD" || valuteModel.CharCode == "EUR"
                        }
                }
            }
        }
    }

    fun refreshOnResume() {
        if (preferenceProvider.currencyEnabled != currencyEnabled.value) {
            _currencyEnabled.value = preferenceProvider.currencyEnabled
            if (currencyEnabled.value) refreshUserData()
        }
    }

    fun updateAuth(dataClient: DataClient) {
        dataClient.getDataItems(Uri.parse("wear://*/token")).addOnSuccessListener {
            preferenceProvider.updateToken(String(it[0].data, Charsets.UTF_8))
            refreshUserData()
        }
    }
}