package com.fefuproject.druzhbank.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.usecase.LogInUseCase
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loginUserUseCase: LogInUseCase,
    val preferenceProvider: PreferenceProvider,
) : ViewModel() {

    fun login(username: String, password: String, context: Context, onSuccessListener: () -> Unit) {
        viewModelScope.launch {
            val user = loginUserUseCase.invoke(username, password)
            if (user != null) {
                preferenceProvider.updateToken(user.token!!)
                val request = PutDataRequest.create("/token").apply {
                    setUrgent()
                    data = user.token!!.toByteArray()
                }
                Wearable.getDataClient(context).putDataItem(request)
                onSuccessListener.invoke()
            } else {
                Toast.makeText(context, "Вход не удался...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}