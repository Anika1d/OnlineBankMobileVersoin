package com.fefuproject.druzhbank.moneytransfer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMoneyTransferBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.moneytransfer.adapter.CardTransferAdapter
import com.fefuproject.druzhbank.moneytransfer.adapter.CardsActionListenerT
import com.fefuproject.druzhbank.payment.PaymentToCardFragment
import com.fefuproject.druzhbank.dirprofile.dircard.CardFragment
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
class MoneyTransferFragment() : Fragment() {
    // TODO: Rename and change types of parameters



    lateinit var tmp_number_card: String
    private var _binding: FragmentMoneyTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoneyTransferViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tmp_number_card = arguments?.get("myArg") as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoneyTransferBinding.inflate(inflater, container, false)
        binding.shimmerCardPayment.startShimmer()
        viewModel.initData(tmp_number_card, binding, this)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this@MoneyTransferFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("myArg", tmp_number_card)
                    val fr = CardFragment()
                    fr.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, fr,
                            "CardFragment"
                        )
                        commit()
                    }

                }
            })
    }
}