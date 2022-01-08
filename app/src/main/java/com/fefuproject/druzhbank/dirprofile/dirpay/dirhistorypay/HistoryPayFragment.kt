package com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentHistoryPayBinding
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.PayFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HistoryPayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binging:FragmentHistoryPayBinding?=null
    private var adapter=PayHistoryAdapter()
    private val binding get() = _binging!!


    private val payHistory=PayHistory(
        nameOperation = "Перевод на карту",
        valueOperation = "12300,00 рублей"
    , dateOperation = "12.12.2023"
    )
    private val payHistory1=PayHistory(
        nameOperation = "Перевод на карту",
        valueOperation = "100,00 рублей"
        , dateOperation = "12.12.2021"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binging= FragmentHistoryPayBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LinearLayoutManager(this@HistoryPayFragment.context).also { binding.recycleViewHistoryPay.layoutManager = it }
        adapter.addPayHistory(payHistory = payHistory)
        adapter.addPayHistory(payHistory = payHistory1)
        adapter.addPayHistory(payHistory = payHistory)
        adapter.addPayHistory(payHistory = payHistory1)
        adapter.addPayHistory(payHistory = payHistory)
        adapter.addPayHistory(payHistory = payHistory1)
        binding.recycleViewHistoryPay.adapter=adapter
        activity?.onBackPressedDispatcher?.addCallback(
            this@HistoryPayFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            PayFragment(),
                            "PayFragment"
                        )
                        commit()
                    }
                }
            })
    }

}