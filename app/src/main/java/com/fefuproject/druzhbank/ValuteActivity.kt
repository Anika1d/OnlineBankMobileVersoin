package com.example.druzhbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.Valute
import com.fefuproject.druzhbank.ValuteAdapter
import com.fefuproject.druzhbank.databinding.ActivityValuteBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ValuteActivity : AppCompatActivity() {
    lateinit var binding: ActivityValuteBinding
    val adapter = ValuteAdapter()


    /////ТУТ ЩАС БУДЕТ ЗАПОЛНЕНИЕ ВАЛЮТ В РУЧНУЮ, ПОТОМ ДОДЕЛАТЬ ДЛЯ GET ЗАПРОСА
    //если нельзя получить нужный тип данных, а дает что либо другое
    //либо пишем вове, либо меняем тип данных в Valute.kt and ValuteAdapter
    val valute1 = Valute(
        isBuyUp = true,
        isResellUp = false,
        price_Resell = 77.1.toFloat(),
        price_Buy = 80.1.toFloat(),
        long_name_valute = "Американский доллар",
        short_name_valute = "USD"

    )
    val valute2 = Valute(
        isBuyUp = false,
        isResellUp = false,
        price_Resell = 60.1.toFloat(),
        price_Buy = 100.1.toFloat(),
        long_name_valute = "Американский доллар",
        short_name_valute = "USD"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValuteBinding.inflate(layoutInflater)
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)
        binding.today.text=dateText.toString()
        setContentView(binding.root)
        initDataValute() //инициализация элментов валют
    }

    private fun initDataValute() {
        binding.apply {
            recValute.layoutManager = LinearLayoutManager(this@ValuteActivity)
            adapter.addValute(valute1)
            adapter.addValute(valute2)
            recValute.adapter = adapter
            recValute.addItemDecoration(CommonItemSpaceDecoration(5));
        }
    }
}