package com.fefuproject.druzhbank.bank

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.dirprofile.pay.PayViewModel
import com.fefuproject.druzhbank.maps.MapsFragment
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.fefuproject.shared.account.domain.models.BankomatModel

@AndroidEntryPoint
class BankActivity : AppCompatActivity() {
    lateinit var binding: ActivityBankBinding
    private val viewModel: BankViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragmentContainerViewMaps,
                MapsFragment(),
                " MapsFragment"
            )
            commit()
        }
        binding.shimmerBank.startShimmer()
        viewModel.drawBank(binding, this@BankActivity)
        setContentView(binding.root)
    }


}
