package com.fefuproject.druzhbank.valute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityValuteBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.shared.account.domain.models.ValuteModel
import com.fefuproject.shared.account.domain.models.ValuteResponseModel
import com.fefuproject.shared.account.domain.usecase.GetValuteUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ValuteActivity : AppCompatActivity() {
    lateinit var binding: ActivityValuteBinding
    val adapter = ValuteAdapter()
    val viewModel: ValuteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValuteBinding.inflate(layoutInflater)
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)
        binding.today.text = dateText.toString()
        setContentView(binding.root)
        binding.shimmerValute.startShimmer()
        viewModel.drawValute(binding,this)
    }


}