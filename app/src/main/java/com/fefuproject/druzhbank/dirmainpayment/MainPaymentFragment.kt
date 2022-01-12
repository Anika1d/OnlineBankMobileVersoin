package com.fefuproject.druzhbank.dirmainpayment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMainPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.PaymentContractFragment
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesActionListener
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesActionListenerH
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesAdapterHorizotal
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesAdapterVertical
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.models.Categories
import com.fefuproject.druzhbank.dirpayment.PaymentToCardFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val categories = Categories(
        id = 1,
        name_categories = "Мобильная связь"
    )
    private val categories2 = Categories(
        id = 2,
        name_categories = "Любимой "
    )

    private val categories3 = Categories(
        id = 3,
        name_categories = "брату "
    )
    private val categories4 = Categories(
        id = 4,
        name_categories = "за инет"
    )
    private var param1: String? = null
    private var param2: String? = null
    private val adapterV = CategoriesAdapterVertical(
        object : CategoriesActionListener {
            override fun OnCategoriesDetails(categories: Categories) {
                super.OnCategoriesDetails(categories)
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        PaymentContractFragment(), "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )


    private val adapterH = CategoriesAdapterHorizotal(
        object : CategoriesActionListenerH {
            override fun OnCategoriesDetails(categories: Categories) {
                super.OnCategoriesDetails(categories)
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        PaymentContractFragment(), "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )
    private var _binding: FragmentMainPaymentBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentMainPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables", "FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(adapterV) {
            addCat(categories)
            addCat(categories2)
            addCat(categories3)
            addCat(categories4)
        }
        with(adapterH) {
            addCat(categories)
            addCat(categories2)
            addCat(categories3)
            addCat(categories4)
        }
        binding.recycleViewCategoriesVertical.addItemDecoration(CommonItemSpaceDecoration(16))
        binding.recycleViewCategoriesVertical.adapter = adapterV
        binding.recycleViewCategoriesHorizotal.adapter = adapterH

    }
}