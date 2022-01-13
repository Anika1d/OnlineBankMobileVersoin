package com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentCardHistoryBinding
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
import com.fefuproject.shared.account.domain.models.CardModel

class CardHistoryFragment(cards: CardModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    val card=cards;
    private var param2: String? = null
    private var _binding: FragmentCardHistoryBinding? =null
    private val binding get() = _binding!!

    private var adapter=CardHistoryAdapter()

    private var cardHistory=CardHistory(
        name = "Перевод на карту",
        date = "12.12.2021"
        , value = 125.5
    )

    private var cardHistory2=CardHistory(
        name = "Оплата кредита",
        date = "13.05.2022"
        , value = 12500.5
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentCardHistoryBinding.inflate(inflater,container,false)
        binding.recycleViewCardsHistory.adapter=adapter
        LinearLayoutManager(this@CardHistoryFragment.context).also { binding.recycleViewCardsHistory.layoutManager = it }
        adapter.addCardHistory(cardHistory)
        adapter.addCardHistory(cardHistory2)
        adapter.addCardHistory(cardHistory)
        adapter.addCardHistory(cardHistory2)
        adapter.addCardHistory(cardHistory)
        adapter.addCardHistory(cardHistory2)
        adapter.addCardHistory(cardHistory)
        adapter.addCardHistory(cardHistory2)
        adapter.addCardHistory(cardHistory)
        adapter.addCardHistory(cardHistory2)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this@CardHistoryFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            CardFragment(card),
                            "CardFragment"
                        )
                        commit()
                    }
                }
            })
    }

}