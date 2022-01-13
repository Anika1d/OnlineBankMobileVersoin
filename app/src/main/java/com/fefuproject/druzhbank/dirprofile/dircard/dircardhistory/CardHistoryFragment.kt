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
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetCardHistoryUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CardHistoryFragment(cards: CardModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    val card = cards;
    private var param2: String? = null
    private var _binding: FragmentCardHistoryBinding? = null
    private val binding get() = _binding!!
    lateinit var cardHistoryList: List<HistoryInstrumentModel>
    private var adapter = CardHistoryAdapter()


    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCardHistoryUseCase: GetCardHistoryUseCase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runBlocking {
            cardHistoryList =
                getCardHistoryUseCase.invoke(card.number, preferenceProvider.token!!, 10)!!
        }
        _binding = FragmentCardHistoryBinding.inflate(inflater, container, false)
        binding.recycleViewCardsHistory.adapter = adapter
        LinearLayoutManager(this@CardHistoryFragment.context).also {
            binding.recycleViewCardsHistory.layoutManager = it
        }
        adapter.addCardHistoryList(cardHistoryList)
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