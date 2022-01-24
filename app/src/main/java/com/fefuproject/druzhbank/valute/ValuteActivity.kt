package com.fefuproject.druzhbank.valute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    @Inject
    lateinit var getValuteuse:GetValuteUseCase
     var valute_res:ValuteResponseModel?=null
    lateinit var valute_list:List<ValuteModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {valute_res=getValuteuse.invoke() }
        binding = ActivityValuteBinding.inflate(layoutInflater)
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)
        binding.today.text=dateText.toString()
        valute_list=valute_res!!.ValCurs.Valute
        adapter.addValuteList(valute_list)
        setContentView(binding.root)
        initDataValute() //инициализация элментов валют
    }

    private fun initDataValute() {
        binding.apply {
            recValute.layoutManager = LinearLayoutManager(this@ValuteActivity)
            recValute.adapter = adapter
            recValute.addItemDecoration(CommonItemSpaceDecoration(5));
        }
    }
}