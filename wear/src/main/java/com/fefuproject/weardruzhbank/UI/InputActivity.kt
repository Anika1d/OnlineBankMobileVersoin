package com.fefuproject.weardruzhbank.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text
import com.fefuproject.weardruzhbank.extensions.DefaultScaffold
import com.fefuproject.weardruzhbank.extensions.InputType


@ExperimentalWearMaterialApi
class InputActivity : ComponentActivity() {
    private lateinit var inputType: InputType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputType = intent.getSerializableExtra("type") as InputType
        setContent {
            DefaultScaffold {
                RootView()
            }
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun RootView() {
        var input by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        Column(
            Modifier
                .padding(0.dp, 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (inputType == InputType.Password) "Введите пин-код" else "Введите сумму в рублях",
                style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(30.dp))
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .size(50.dp, 50.dp),
                value = input,
                onValueChange = { input = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    setResult(
                        RESULT_OK,
                        Intent().apply {
                            if (inputType == InputType.Number) putExtra(
                                "number",
                                input.toInt()
                            ) else putExtra("PIN", input)
                        })
                    finish()
                }),
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                cursorBrush = SolidColor(Color.White)
            )
        }
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}