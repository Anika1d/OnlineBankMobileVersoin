package com.fefuproject.weardruzhbank.UI.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.usecase.GetUserUseCase
import com.fefuproject.weardruzhbank.Model.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val preferenceProvider: PreferenceProvider
) :
    ViewModel() {
    private val _username = MutableStateFlow<String?>(null)
    val username get() = _username.asStateFlow()
    private val _tokenExists = MutableStateFlow(preferenceProvider.token != null)
    val tokenExists get() = _tokenExists.asStateFlow()

    init {
        viewModelScope.launch {
            if (tokenExists.value)
                _username.value = getUserUseCase.invoke(preferenceProvider.token!!).name
        }
    }
}