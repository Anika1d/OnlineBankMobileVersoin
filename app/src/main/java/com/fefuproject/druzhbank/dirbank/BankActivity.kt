package com.fefuproject.druzhbank.dirbank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration


class BankActivity : AppCompatActivity() {
    lateinit var binding: ActivityBankBinding
    private val adapter = BanksAdapter()

    /////ТУТ ЩАС БУДЕТ ЗАПОЛНЕНИЕ БАНКА В РУЧНУЮ, ПОТОМ ДОДЕЛАТЬ ДЛЯ GET ЗАПРОСА
    //если нельзя получить нужный тип данных, а дает что либо другое
    //либо пишем вове, либо меняем тип данных в Bank.kt and BanksAdapter
    val bank1: Banks = Banks(
        street = "Владивосток ул. Вторая речка д. 32А",
        isWorked = true,              //ТУТ ПОЛУЧАЕМ РАБОТАЕТ ИЛИ НЕТ (вдруг идет ремонт),
        // ЕСЛИ МОЖНО ПОЛУЧИТЬ
        ///И  СРАВНЕНИЕ ВРЕМЕНИ РАБОТЫ С ТЕКУЩИМ ВРЕМЕНЕМ ЕСЛИ НЕТ ЧП СИТУАЦИЙ
        Department = "Отделение",
        time_work = "00:00 - 20:00 "
    )
    val bank2: Banks = Banks(
        street = "Владивосток ул. Первая речка д. 3",
        isWorked = false,
        Department = "Банкомант",
        time_work = "00:00 - 00:00 "
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        initDataBanks() //инициализация элментов банков
        setContentView(binding.root)
    }

    private fun initDataBanks() {
        binding.apply {
            recBank.layoutManager = LinearLayoutManager(this@BankActivity)
            recBank.addItemDecoration(CommonItemSpaceDecoration(5));
            adapter.addBank(bank1)
            adapter.addBank(bank2)
            recBank.adapter = adapter
        }
    }
}
