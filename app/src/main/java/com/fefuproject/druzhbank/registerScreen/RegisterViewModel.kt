package com.fefuproject.druzhbank.registerScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityRegisterBinding
import com.fefuproject.druzhbank.payment.PaymentToCardFragment
import com.fefuproject.shared.account.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("StaticFieldLeak")
    private lateinit var registerActivity: RegisterActivity
    fun initData(_binding: ActivityRegisterBinding, _registerActivity: RegisterActivity) {
        binding = _binding
        registerActivity = _registerActivity
        inputFocusListener()
        binding.buttonSecond.setOnClickListener {
            viewModelScope.launch {
                val validPass = binding.textInputPassCopy.helperText != null
                val validPassCopy = binding.textInputPass.helperText != null
                val validLogin = binding.textInputLog.helperText != null
                val validName = binding.textInputName.helperText != null
                val password_с = binding.pcEd.text.toString()
                val password = binding.pEd.text.toString()
                val login = binding.loginEdit.text.toString()
                val name = binding.nameEdit.text.toString()
                if (validPass && validPassCopy && validLogin && validName
                ) {
                    Toast.makeText(
                        registerActivity,
                        "Неверный номер карты",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (password == password_с && password.length > 5 && login.length > 5 && login.length < 30
                        && name.matches(Regex("[A-z]+")) && name.length > 5 && name.length < 30
                    ) {
                        signUpUseCase.invoke(name, login, password)
                        Toast.makeText(
                            registerActivity,
                            "Регистрация прошла успешно",
                            Toast.LENGTH_LONG
                        ).show()
                        registerActivity.finish()
                    } else {
                        Toast.makeText(
                            registerActivity,
                            "неверные значения",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }
    }


    fun inputFocusListener() {
        loginFocusListener()
        nameFocusListener()
        passwordFocusListener()
        passwordCopyFocusListener()
    }

    private fun nameFocusListener() {
        binding.textInputName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputName.helperText = validName()
            }
        }
    }

    private fun loginFocusListener() {
        binding.textInputLog.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputLog.helperText = validLogin()
            }
        }
    }

    private fun passwordFocusListener() {
        binding.textInputPass.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputPass.helperText = validPassword()
            }
        }
    }

    private fun passwordCopyFocusListener() {
        binding.textInputPassCopy.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputPassCopy.helperText = validPasswordCopy()
            }
        }
    }

    private fun validPasswordCopy(): String? {
        val password_c = binding.pcEd.text.toString()
        val password = binding.pEd.text.toString()
        if (password != password_c)
            return "Пароли не совпадают"
        if (password_c.length < 6)
            return "Пароль должно содержать не меньше 6 символов"
        if (30 < password_c.length)
            return "Пароль должно быть короче 30 символов"
        return null
    }

    private fun validLogin(): String? {
        val login = binding.loginEdit.text.toString()
//        if (!name.matches(Regex("[A-z]+"))) {
//            return "Имя может содержать только буквы"
//        }
        if (login.length < 6)
            return "Логин должно содержать не меньше 6 символов"
        if (30 < login.length)
            return "Логин должно быть короче 30 символов"
        return null
    }

    private fun validName(): String? {
        val name = binding.nameEdit.text.toString()
        if (!name.matches(Regex("[A-z]+"))) {
            return "Имя может содержать только буквы"
        }
        if (name.length < 6)
            return "Имя должно содержать не меньше 6 символов"
        if (30 < name.length)
            return "Имя должно быть короче 30 символов"
        return null
    }


    private fun validPassword(): String? {
        val password = binding.pEd.text.toString()
        if (password.length < 6)
            return "Пароль должно содержать не меньше 6 символов"
        if (30 < password.length)
            return "Пароль должно быть короче 30 символов"
        return null
    }


}