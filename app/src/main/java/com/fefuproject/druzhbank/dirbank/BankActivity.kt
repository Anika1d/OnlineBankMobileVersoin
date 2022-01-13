package com.fefuproject.druzhbank.dirbank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.repository.AccountRepository
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentsUseCase
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.fefuproject.shared.account.domain.models.BankomatModel
@AndroidEntryPoint
class BankActivity: AppCompatActivity() {
    lateinit var binding: ActivityBankBinding
    private val adapter = BanksAdapter()
    lateinit var bank_list:List<BankomatModel>
    @Inject
    lateinit var getBankomatsUseCase: GetBankomatsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {bank_list=getBankomatsUseCase.invoke() }
        binding = ActivityBankBinding.inflate(layoutInflater)
        initDataBanks() //инициализация элментов банков
        setContentView(binding.root)
    }

    private fun initDataBanks() {
        adapter.addBankList(bank_list)
        binding.apply {
            recBank.layoutManager = LinearLayoutManager(this@BankActivity)
            recBank.addItemDecoration(CommonItemSpaceDecoration(5))
            recBank.adapter = adapter
        }
    }
}
