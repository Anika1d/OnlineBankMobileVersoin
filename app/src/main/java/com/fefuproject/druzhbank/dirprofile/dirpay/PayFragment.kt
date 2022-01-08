package com.fefuproject.druzhbank.dirprofile.dirpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPayBinding
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistoryFragment
import com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay.HistoryPayFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//startPosition:Int
class PayFragment: Fragment() {
 ///   private val pos=startPosition
    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!

    private val pays = Pays(
        namePay = "Пенсия", valuePay = "12000 рублей", numberPay = "****9999"
    )
    private val pays1 = Pays(
        namePay = "Накопления", valuePay = "912000 рублей", numberPay = "****9888"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PayImageAdapter(this)//,pos
        binding.recycleViewPayFull.adapter = adapter
        adapter.addPay(pays1)
        adapter.addPay(pays)
        adapter.addPay(pays1)
        adapter.addPay(pays)
        adapter.addPay(pays1)
        binding.historyCardButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                val visibleFragment =
                    parentFragmentManager.fragments.firstOrNull { !isHidden }
                visibleFragment?.let {
                    hide(visibleFragment)
                }
                replace(
                    R.id.fragmentContainerViewProfile,
                    HistoryPayFragment(), "PayHistory"
                )
                commit()
            }
        }
    }


}