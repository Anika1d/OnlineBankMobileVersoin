package com.fefuproject.druzhbank.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.UserModel
import com.fefuproject.shared.account.domain.usecase.ChangePasswordUseCase
import com.fefuproject.shared.account.domain.usecase.ChangeUsernameUseCase
import com.fefuproject.shared.account.domain.usecase.GetUserUseCase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val preferenceProvider: PreferenceProvider,
    private val changeUsernameUseCase: ChangeUsernameUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel() {
    private lateinit var modelUser: UserModel
    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("StaticFieldLeak")
    private lateinit var profileActivity: ProfileActivity

    fun initData(_binding: ActivityProfileBinding, _profileActivity: ProfileActivity) {
        binding = _binding
        profileActivity = _profileActivity
        viewModelScope.launch {
            modelUser = getUserUseCase.invoke(preferenceProvider.token!!)!!
            binding.includeToolbar.namePesone.text = modelUser.name

        }
    }


    @SuppressLint("SetTextI18n")
    fun AlertDialogChange(tagChange: String) { //создание диалога авторизации
        val builder = AlertDialog.Builder(profileActivity)
        val alert = builder.create();
        val promptsView: View =
            LayoutInflater.from(profileActivity).inflate(R.layout.change_private_data_in_user, null)
        alert.setView(promptsView)
        alert.setCancelable(false)
        val bun_cancel = promptsView.findViewById<MaterialButton>(R.id.cancel_button_change)
        bun_cancel.setOnClickListener {
            alert.dismiss()
        }
        val textView = promptsView.findViewById<TextView>(R.id.change_your)
        textView.text = textView.text.toString() + tagChange
        val textView2 = promptsView.findViewById<TextView>(R.id.enter_your)
        textView2.text = textView2.text.toString() + tagChange
        if (tagChange != "имя") {
            val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
            editText.hint = "Введите ваш новый $tagChange"
            val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
            inputTextLayout.hint = "Новый $tagChange"

            val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)

            bun_ch_d.setOnClickListener {
                viewModelScope.launch {
                    changePasswordUseCase.invoke(
                        token = preferenceProvider.token!!,
                        new_password = editText.text.toString(),
                        old_password = ""
                    )
                }
                Toast.makeText(
                    profileActivity.applicationContext,
                    "Вы изменили $tagChange",
                    Toast.LENGTH_SHORT
                ).show()
                alert.dismiss()
            }
        } else {
            val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
            editText.hint = "Введите ваше новое $tagChange"
            val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
            inputTextLayout.hint = "Новое $tagChange"

            val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)

            bun_ch_d.setOnClickListener {
                viewModelScope.launch {
                    changeUsernameUseCase.invoke(
                        token = preferenceProvider.token!!,
                        username = editText.text.toString()
                    )
                    Toast.makeText(
                        profileActivity.applicationContext,
                        "Вы изменили $tagChange",
                        Toast.LENGTH_SHORT
                    ).show()
                    alert.dismiss()
                }
                binding.includeToolbar.namePesone.text=editText.text
            }
        }
        alert.show()
    }


}
