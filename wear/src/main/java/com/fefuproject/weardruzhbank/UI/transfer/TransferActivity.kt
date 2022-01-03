package com.fefuproject.weardruzhbank.UI.transfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.weardruzhbank.di.AuthStateObserver
import com.fefuproject.weardruzhbank.R
import com.fefuproject.weardruzhbank.extensions.DefaultScaffold
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class TransferActivity : ComponentActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultScaffold(verificationState = authObserver.verificationState) {
                RootView()
            }
        }
    }

    @Composable
    fun RootView(viewModel: TransferViewModel = hiltViewModel()) {
        val isLoading by viewModel.isLoading.collectAsState()
        val sourceToolListState = rememberScalingLazyListState()
        val targetToolListState = rememberScalingLazyListState()
        val selectionError =
            sourceToolListState.layoutInfo.centralItemIndex == targetToolListState.layoutInfo.centralItemIndex
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Text("Выберите счета", style = TextStyle(fontWeight = FontWeight.Light))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ToolSelector(listState = sourceToolListState, isError = selectionError)
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                        contentDescription = "arrow"
                    )
                    ToolSelector(listState = targetToolListState, isError = selectionError)
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier,
                        onClick = { viewModel.startTransaction() },
                        enabled = !selectionError
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_done_24),
                            contentDescription = "done"
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ToolSelector(listState: ScalingLazyListState, isError: Boolean) {
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.width(50.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(25.dp))
            }
            items(20) {
                Text(
                    "Тест",
                    style = TextStyle(
                        fontWeight =
                        if (listState.layoutInfo.centralItemIndex - 1 == it)
                            FontWeight.ExtraBold else FontWeight.Normal,
                        color = if (it == listState.layoutInfo.centralItemIndex - 1 && isError)
                            Color.Red else Color.White
                    )
                ) //TODO
            }
            item {
                Spacer(modifier = Modifier.size(25.dp))
            }
        }
    }
}