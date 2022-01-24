package com.fefuproject.druzhbank.UI.transfer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.UI.InputActivity
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.fefuproject.druzhbank.extensions.InputType
import com.fefuproject.druzhbank.extensions.roundedPlaceholder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class TransferActivity : ComponentActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver
    val viewModel: TransferViewModel by viewModels()
    private lateinit var authLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contract = ActivityResultContracts.StartActivityForResult()
        authLauncher = registerForActivityResult(contract) { result ->
            if (result.resultCode == RESULT_OK) {
                val res = result.data!!.getIntExtra("number", -1)
                if (res > 0)
                    viewModel.startTransaction(
                        res
                    ) {
                        Toast.makeText(this, "Перевод выполнен успешно!", Toast.LENGTH_SHORT).show()
                    }
                else
                    Toast.makeText(this, "Введите корректную сумму", Toast.LENGTH_SHORT).show()
            }
        }
        setContent {
            DefaultScaffold(verificationStateObserver = authObserver) {
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
            sourceToolListState.centerItemIndex == targetToolListState.centerItemIndex
                    || (sourceToolListState.centerItemIndex == sourceToolListState.centerItemIndex - 1)
                    || (targetToolListState.centerItemIndex == targetToolListState.centerItemIndex - 1)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                    ToolSelector(
                        listState = sourceToolListState,
                        isError = selectionError,
                        isLeft = true
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                        contentDescription = "arrow"
                    )
                    ReverseToolSelector(
                        listState = targetToolListState,
                        isError = selectionError,
                        isLeft = false
                    )
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier,
                        onClick = {
                            viewModel.transactionBundle = PaymentRequestBundle(
                                viewModel.sourceCards.value!![sourceToolListState.centerItemIndex - 1],
                                viewModel.sourceCards.value!![targetToolListState.centerItemIndex - 1]
                            )
                            authLauncher.launch(
                                Intent(
                                    this@TransferActivity,
                                    InputActivity::class.java
                                ).apply {
                                    putExtra(
                                        "type",
                                        InputType.Number
                                    )
                                })
                        },
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
    fun ToolSelector(
        listState: ScalingLazyListState,
        isError: Boolean,
        isLeft: Boolean,
        viewModel: TransferViewModel = hiltViewModel()
    ) {
        val cards by viewModel.sourceCards.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.width(100.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(cards?.size ?: 3) {
                Row(
                    horizontalArrangement = if (isLeft) Arrangement.End else Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            cards!!.get(it).instrumentType!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.centerItemIndex - 1 == it)
                                    FontWeight.Normal else FontWeight.Light,
                                color = if (it == listState.centerItemIndex - 1 && isError)
                                    Color.Red else Color.White,
                                fontSize = 10.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            if (cards!![it].name.isNullOrBlank()) "*" + cards!![it].number!!.takeLast(
                                4
                            ) else cards!![it].name!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.centerItemIndex - 1 == it)
                                    FontWeight.ExtraBold else FontWeight.Normal,
                                color = if (it == listState.centerItemIndex - 1 && isError)
                                    Color.Red else Color.White
                            )
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }

    @Composable
    fun ReverseToolSelector( //THX GOOGLE FOR WORKING ZINDEX YEAH THAT'S SO GOOD THXXXXXXXXXX
        listState: ScalingLazyListState,
        isError: Boolean,
        isLeft: Boolean,
        viewModel: TransferViewModel = hiltViewModel()
    ) {
        val cards by viewModel.sourceCards.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.width(100.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(cards?.size ?: 3) {
                Row(
                    horizontalArrangement = if (isLeft) Arrangement.End else Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true),
                            fontSize = 10.sp
                        )
                    } else {
                        Text(
                            if (cards!![it].name.isNullOrBlank()) "*" + cards!![it].number!!.takeLast(
                                4
                            ) else cards!![it].name!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.centerItemIndex - 1 == it)
                                    FontWeight.ExtraBold else FontWeight.Normal,
                                color = if (it == listState.centerItemIndex - 1 && isError)
                                    Color.Red else Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true),
                            fontSize = 10.sp
                        )
                    } else {
                        Text(
                            cards!!.get(it).instrumentType!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.centerItemIndex - 1 == it)
                                    FontWeight.Normal else FontWeight.Light,
                                color = if (it == listState.centerItemIndex - 1 && isError)
                                    Color.Red else Color.White,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}
