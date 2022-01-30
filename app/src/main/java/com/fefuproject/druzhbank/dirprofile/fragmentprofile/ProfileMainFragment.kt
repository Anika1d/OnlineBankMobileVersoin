package com.fefuproject.druzhbank.dirprofile.fragmentprofile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirprofile.credit.CreditsAdapter
import com.fefuproject.druzhbank.dirprofile.dircard.*
import com.fefuproject.druzhbank.dirprofile.pay.PayFragment
import com.fefuproject.druzhbank.dirprofile.pay.PaysActionListener
import com.fefuproject.druzhbank.dirprofile.pay.PaysAdapter
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.models.CreditModel
import com.fefuproject.shared.account.domain.usecase.GetCardsUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import com.fefuproject.shared.account.domain.usecase.GetCreditsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class ProfileMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileMainBinding? = null
    private val binding get() = _binding!!

    private var cardsAdapter = CardsAdapter(
        object : CardsActionListener {
            override fun onCardDetails(card: CardModel) {
                val bundle = Bundle()
                bundle.putString("myArg", card.number)
                val fr = CardFragment()
                fr.arguments = bundle
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "CardFragment"
                    )
                    commit()
                }
            }
        }

    )
    private val paysAdapter = PaysAdapter(
        object : PaysActionListener {
            override fun onPayDetails(pay: CheckModel) {
                super.onPayDetails(pay)
                val bundle = Bundle()
                bundle.putString("myArg", pay.number)
                val fr = PayFragment()
                fr.arguments = bundle
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "PayFragment"
                    )
                    commit()
                }
            }
        }
    )

    private val creditsAdapter = CreditsAdapter()

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCheckUseCase: GetChecksUseCase

    @Inject
    lateinit var getCreditsUseCase: GetCreditsUseCase

    @Inject
    lateinit var getCardsUseCase: GetCardsUseCase
    lateinit var card_list: List<CardModel>
    lateinit var credits_list: List<CreditModel>
    lateinit var check_list: List<CheckModel>
    private var backButtonCount=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            credits_list = getCreditsUseCase.invoke(preferenceProvider.token!!)!!
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
        }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            card_list = getCardsUseCase.invoke(preferenceProvider.token!!)!!
            credits_list = getCreditsUseCase.invoke(preferenceProvider.token!!)!!
            check_list = getCheckUseCase.invoke(preferenceProvider.token!!)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recycleViewCards
        LinearLayoutManager(this@ProfileMainFragment.context).also {
            binding.recycleViewCards.layoutManager = it
        }
        cardsAdapter.addListCard(card_list)
        recyclerView.adapter = cardsAdapter
        initDataRec()
        creditsAdapter.addCreditList(credits_list)
        paysAdapter.addPayList(check_list)


//        activity?.onBackPressedDispatcher?.addCallback(
//            this@ProfileMainFragment,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    if (backButtonCount >= 1) {
//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.addCategory(Intent.CATEGORY_HOME)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    } else {
//                       makeText(
//                            this@ProfileMainFragment.context,
//                            "Нажмите еще раз,чтобы выйти из приложения.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        backButtonCount++
//                    }
//                }
//            })

    }

    private fun initDataRec() {
        binding.apply {

            LinearLayoutManager(this@ProfileMainFragment.context).also {
                recycleViewCredits.layoutManager = it
            }
            recycleViewCredits.adapter = creditsAdapter
            LinearLayoutManager(this@ProfileMainFragment.context).also {
                recycleViewPay.layoutManager = it
            }
            recycleViewPay.addItemDecoration(CommonItemSpaceDecoration(5))
            recycleViewCredits.addItemDecoration(CommonItemSpaceDecoration(5))
            recycleViewCards.addItemDecoration(CommonItemSpaceDecoration(5))
            recycleViewPay.adapter = paysAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileMainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}