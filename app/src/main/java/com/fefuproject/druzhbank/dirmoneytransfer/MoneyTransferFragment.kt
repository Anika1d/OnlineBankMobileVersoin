package com.fefuproject.druzhbank.dirmoneytransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMoneyTransferBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.dirmoneytransfer.diradapter.CardTransferAdapter
import com.fefuproject.druzhbank.dirprofile.dircard.Cards

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoneyTransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoneyTransferFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentMoneyTransferBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val cards = Cards(
        typeCard = "Кредитка",
        valueCard = "2140000 рублей", whatBank = "mir",
        numberCard = "2145 1245 **** ****", isBlocked = false
    )

    private val cards1 = Cards(
        typeCard = "Зарплатная ",
        valueCard = "40000 рублей", whatBank = "mir",
        numberCard = "3333 9999 **** ****", isBlocked = false
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentMoneyTransferBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     val adapter= CardTransferAdapter()
        adapter.addCard(card = cards)
        adapter.addCard(card = cards1)
        adapter.addCard(card = cards)
        adapter.addCard(card = cards1)
        adapter.addCard(card = cards)
        adapter.addCard(card = cards1)
        LinearLayoutManager(this.context).also {
            binding.recycleViewPayment.layoutManager = it
        }
        binding.recycleViewPayment.addItemDecoration(CommonItemSpaceDecoration(25))
        binding.recycleViewPayment.adapter=adapter
    }
}