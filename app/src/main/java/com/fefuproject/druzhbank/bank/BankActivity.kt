package com.fefuproject.druzhbank.bank

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ActivityBankBinding
import com.fefuproject.druzhbank.maps.MapsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankActivity : AppCompatActivity() {
    lateinit var binding: ActivityBankBinding
    private val viewModel: BankViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankBinding.inflate(layoutInflater)
        val fr = MapsFragment()
        viewModel.pushFragment(fr)
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragmentContainerViewMaps,
                fr,
                " MapsFragment"
            )
            commit()
        }
        binding.shimmerBank.startShimmer()
        viewModel.drawBank(binding, this@BankActivity)
        setContentView(binding.root)
    }


}
