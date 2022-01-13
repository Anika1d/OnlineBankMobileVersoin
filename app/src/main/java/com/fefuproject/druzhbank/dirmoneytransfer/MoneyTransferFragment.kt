package com.fefuproject.druzhbank.dirmoneytransfer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMoneyTransferBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirmainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.dirmoneytransfer.diradapter.CardTransferAdapter
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class MoneyTransferFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var getCardsUseCase: GetCardsUseCase

    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    lateinit var card_list: List<CardModel>;

    private var _binding: FragmentMoneyTransferBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            preferenceProvider.cardList = getCardsUseCase.invoke(preferenceProvider.token!!)!!
        }
        card_list = preferenceProvider.cardList
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onResume() {
        super.onResume()
        runBlocking {
            preferenceProvider.cardList = getCardsUseCase.invoke(preferenceProvider.token!!)!!
        }
        card_list = preferenceProvider.cardList
    }
    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoneyTransferBinding.inflate(inflater, container, false)
        activity?.onBackPressedDispatcher?.addCallback(
            this@MoneyTransferFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, ProfileMainFragment(),
                            "ProfileMainFragment"
                        )
                        commit()
                    }
                }
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CardTransferAdapter()

        LinearLayoutManager(this.context).also {
            binding.recycleViewPayment.layoutManager = it
        }
        adapter.addListCard(card_list)
        binding.recycleViewPayment.addItemDecoration(CommonItemSpaceDecoration(25))
        binding.recycleViewPayment.adapter = adapter
    }
}