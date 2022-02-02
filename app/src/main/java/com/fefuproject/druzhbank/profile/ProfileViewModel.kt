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
            passwordFocusListener(inputTextLayout, editText)
            bun_ch_d.setOnClickListener {
                viewModelScope.launch {
                    val validPass = inputTextLayout.helperText != null
                    val pass = editText.text.toString()
                    if (validPass || pass.length < 5) {
                        Toast.makeText(
                            profileActivity,
                            "Не корректные данные",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        changePasswordUseCase.invoke(
                            token = preferenceProvider.token!!,
                            new_password = pass,
                            old_password = ""

                        )
                        Toast.makeText(
                            profileActivity.applicationContext,
                            "Вы изменили $tagChange",
                            Toast.LENGTH_SHORT
                        ).show()
                        alert.dismiss()
                    }
                }

            }
        } else {
            val editText = promptsView.findViewById<TextInputEditText>(R.id.new_password)
            editText.hint = "Введите ваше новое $tagChange"
            val inputTextLayout = promptsView.findViewById<TextInputLayout>(R.id.textInputNewPass)
            inputTextLayout.hint = "Новое $tagChange"
            nameFocusListener(inputTextLayout, editText)
            val bun_ch_d = promptsView.findViewById<MaterialButton>(R.id.change_button_data)
            bun_ch_d.setOnClickListener {
                viewModelScope.launch {
                    val validName = inputTextLayout.helperText != null
                    val name = editText.text.toString()
                    if (validName || name.length < 5 ||
                        !name.matches(Regex("[A-z]+")) || name.length > 30
                    ) {
                        Toast.makeText(
                            profileActivity,
                            "Не корректные данные",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
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
                }
                binding.includeToolbar.namePesone.text = editText.text
            }
        }
        alert.show()
    }

    private fun nameFocusListener(inputTextLayout: TextInputLayout, editText: TextInputEditText) {
        inputTextLayout.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                inputTextLayout.helperText = validName(editText)
            }
        }
    }

    private fun passwordFocusListener(
        inputTextLayout: TextInputLayout,
        editText: TextInputEditText
    ) {
        inputTextLayout.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                inputTextLayout.helperText = validPassword(editText)
            }
        }
    }

    private fun validName(editText: TextInputEditText): String? {
        val name = editText.text.toString()
        if (!name.matches(Regex("[A-z]+"))) {
            return "Имя может содержать только буквы"
        }
        if (name.length < 6)
            return "Имя должно содержать не меньше 6 символов"
        if (30 < name.length)
            return "Имя должно быть короче 30 символов"
        return null
    }


    private fun validPassword(editText: TextInputEditText): String? {
        val password = editText.text.toString()
        if (password.length < 6)
            return "Пароль должно содержать не меньше 6 символов"
        if (30 < password.length)
            return "Пароль должно быть короче 30 символов"
        return null
    }


}
