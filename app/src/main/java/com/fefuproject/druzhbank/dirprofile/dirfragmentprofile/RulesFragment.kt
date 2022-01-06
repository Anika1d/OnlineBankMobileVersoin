package com.fefuproject.druzhbank.dirprofile.dirfragmentprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.RulesFragmentBinding
import android.text.Spanned

import android.text.style.URLSpan

import android.text.SpannableString
import android.content.Intent
import android.net.Uri


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RulesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RulesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: RulesFragmentBinding? = null
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
        // Inflate the layout for this fragment
        _binding = RulesFragmentBinding.inflate(inflater, container, false)
        val ss = SpannableString(binding.agreeRules.text)
        ss.setSpan(
            URLSpan(java.lang.String.valueOf(binding.agreeRules.text)), 0,
            binding.agreeRules.text.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.agreeRules.text = ss
        binding.agreeRules.setOnClickListener {
            val address: Uri =
                Uri.parse("https://www.avangard.ru/rus/docs/rules_cart_use_in_system_mobile_pays.pdf")
            val openlink = Intent(Intent.ACTION_VIEW, address)
            startActivity(openlink)
        }

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RulesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RulesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}