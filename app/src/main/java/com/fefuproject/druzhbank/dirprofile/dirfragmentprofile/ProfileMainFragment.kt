package com.fefuproject.druzhbank.dirprofile.dirfragmentprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.dirprofile.dircredit.Credits
import com.fefuproject.druzhbank.dirprofile.dircredit.CreditsAdapter
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays
import com.fefuproject.druzhbank.dirprofile.dirpay.PaysAdapter
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import com.fefuproject.druzhbank.dirprofile.dircard.*
import java.io.Console
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileMainBinding? = null
    private val binding get() = _binding!!
    private val cardService = CardService()
    private var cardsAdapter = CardsAdapter(
        object : CardsActionListener {
            override fun onCardDetails(card: Cards) {
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        CardFragment(cards = card), "CardFragment"
                    )
                    commit()
                }
            }
        }

    )
    private val paysAdapter = PaysAdapter()
    private val creditsAdapter = CreditsAdapter()


    private val credits = Credits(
        nameCredit = "Рассрочка на наушники",
        valueCredit = "15000 рублей",
        dateCredit = "Платеж 10.01.22"
    )
    private val credits1 = Credits(
        nameCredit = "Ипотека",
        valueCredit = "15314444000 рублей",
        dateCredit = "Платеж 15.01.22"
    )


    private val cards = Cards(
        typeCard = "Кредитка",
        valueCard = "2140000 рублей", whatBank = "mir",
        numberCard = "2145 1245 **** ****"
        , isBlocked = false
    )

    private val cards1 = Cards(
        typeCard = "Зарплатная ",
        valueCard = "40000 рублей", whatBank = "mir",
        numberCard = "3333 9999 **** ****"
        , isBlocked = false
    )

    private val pays = Pays(
        namePay = "Пенсия", valuePay = "12000 рублей", numberPay = "****9999"
    )
    private val pays1 = Pays(
        namePay = "Накопления", valuePay = "912000 рублей", numberPay = "****9888"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recycleViewCards
        LinearLayoutManager(this@ProfileMainFragment.context).also {
            binding.recycleViewCards.layoutManager = it
        }
        recyclerView.adapter = cardsAdapter
        cardsAdapter.addCard(cards)
        cardsAdapter.addCard(cards1)
        cardsAdapter.addCard(cards)
        cardsAdapter.addCard(cards1)
        initDataRec()

    }

    private fun initDataRec() {
        binding.apply {
            creditsAdapter.addCredit(credits)
            creditsAdapter.addCredit(credits1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)

            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            LinearLayoutManager(this@ProfileMainFragment.context).also {
                recycleViewCredits.layoutManager = it
            }
            recycleViewCredits.adapter = creditsAdapter
            LinearLayoutManager(this@ProfileMainFragment.context).also {
                recycleViewPay.layoutManager = it
            }
            recycleViewPay.adapter = paysAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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