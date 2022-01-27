package com.fefuproject.druzhbank.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.databinding.ActivityMainBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.ValuteModel
import com.fefuproject.shared.account.domain.models.ValuteResponseModel
import com.fefuproject.shared.account.domain.usecase.GetValuteUseCase
import com.fefuproject.shared.account.domain.usecase.LogInUseCase
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loginUserUseCase: LogInUseCase,
    val preferenceProvider: PreferenceProvider,
    private val getValuteUseCase: GetValuteUseCase
) : ViewModel() {
    lateinit var valute_res: ValuteResponseModel
    lateinit var valute_list: List<ValuteModel>
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

    @SuppressLint("SetTextI18n")
    fun checkValute(binding: ActivityMainBinding) {
        viewModelScope.launch {
            valute_res = getValuteUseCase.invoke()
            valute_list = valute_res.ValCurs.Valute
            binding.includeButtonValute.eurText.text = "EUR ${valute_list[10].Value}" ///ТАКЖЕ ИЗ БД
            binding.includeButtonValute.usdText.text = "USD ${valute_list[11].Value}"
            binding.includeButtonValute.eurText.visibility = View.VISIBLE
            binding.includeButtonValute.usdText.visibility = View.VISIBLE
            binding.includeButtonValute.shimmerText.stopShimmer()
            binding.includeButtonValute.shimmerText.visibility = View.GONE
        }
    }
}