package com.fefuproject.druzhbank.dirmainpayment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMainPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.PaymentContractFragment
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesActionListener
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesActionListenerH
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesAdapterHorizotal
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters.CategoriesAdapterVertical
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.models.Categories
import com.fefuproject.druzhbank.dirmainpayment.dirsample.SampleFragment
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.usecase.GetCategoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainPaymentFragment : Fragment() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    @Inject
    lateinit var getCategoryUseCase: GetCategoryUseCase
    lateinit var sample_list:List<CategoryModel>
    private var param1: String? = null
    private var param2: String? = null
    private val adapterV = CategoriesAdapterVertical(
        object : CategoriesActionListener {
            override fun OnCategoriesDetails(categories:CategoryModel) {
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
            override fun OnCategoriesDetails(categories: CategoryModel) {
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
        runBlocking { sample_list=getCategoryUseCase.invoke()!!
        }
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
        adapterV.addCatList(sample_list)

        adapterH.addCatList(sample_list)
        binding.recycleViewCategoriesVertical.addItemDecoration(CommonItemSpaceDecoration(16))
        binding.recycleViewCategoriesVertical.adapter = adapterV
        binding.recycleViewCategoriesHorizotal.adapter = adapterH
        cliker_all_shablons()
    }

    private fun cliker_all_shablons() {
        val text_ = binding.allShablons.text
        val s_r = SpannableString(text_);
        val clickableSpanCon = object : ClickableSpan() {
            override fun onClick(widget: View) {
                activity!!.supportFragmentManager.beginTransaction().apply {
                    val visibleFragment =
                        activity!!.supportFragmentManager.fragments.firstOrNull { !isHidden }
                    visibleFragment?.let {
                        hide(visibleFragment)
                    }
                    replace(
                        R.id.fragmentContainerViewProfile,
                        SampleFragment(), "SampleFragment"
                    )
                    commit()
                }

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.setColor(0xFFFFFFFF.toInt());
                ds.setUnderlineText(false)
            }
        }
        s_r.setSpan(
            clickableSpanCon,
            0,
            text_.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.allShablons.movementMethod = LinkMovementMethod.getInstance()
        binding.allShablons.text = s_r


    }
}