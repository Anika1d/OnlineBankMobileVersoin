package com.fefuproject.druzhbank.mainpayment

import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMainPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.mainpayment.paymentcontract.PaymentContractFragment
import com.fefuproject.druzhbank.mainpayment.sample.SampleFragment
import dagger.hilt.android.AndroidEntryPoint

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


    private val viewModel: MainPaymentViewModel by viewModels()
    private var param1: String? = null
    private var param2: String? = null
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
        binding.shimmerCircle.startShimmer()
        binding.shimmerRect.startShimmer()
        viewModel.initData(binding, this)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables", "FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTemplates.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragmentContainerViewProfile,
                PaymentContractFragment(), "PaymentContractFragment"
            )
            commit()
        }
        }
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
                ds.setColor(Color.BLACK);
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