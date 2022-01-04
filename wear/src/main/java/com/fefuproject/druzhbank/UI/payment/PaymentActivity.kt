package com.fefuproject.druzhbank.UI.payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEventBuffer
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalWearMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), DataClient.OnDataChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun RootView(viewModel: PaymentActivityViewModel = hiltViewModel()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Не удалось получить ни одного шаблона...",
                textAlign = TextAlign.Center
            )
            Text(
                text = "Создайте несколько в приложении",
                textAlign = TextAlign.Center
            )
        }
    }

    override fun onDataChanged(p0: DataEventBuffer) {
        TODO("Not yet implemented")
    }
}