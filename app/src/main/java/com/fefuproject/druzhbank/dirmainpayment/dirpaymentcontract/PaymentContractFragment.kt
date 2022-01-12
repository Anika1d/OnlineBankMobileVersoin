package com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentPaymentContractBinding
import com.fefuproject.druzhbank.dirmainpayment.MainPaymentFragment
import com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.ProfileMainFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentContractFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentContractFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPaymentContractBinding? = null
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
        _binding = FragmentPaymentContractBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.textCard.text="Выберете карту"
        val nameCard = arrayOf("Карта 1","Карта 2","Карта 3")
        binding.spinner.adapter=ArrayAdapter<String>(activity!!.applicationContext,android.R.layout.simple_list_item_1,nameCard)
        binding.spinner.onItemSelectedListener=object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.textCard.text="Выбрана карта "+ nameCard[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.textCard.text="Выберете карту"
            }
        }
        binding.materialButton.setOnClickListener {
            makeText(this.context,"Оплачено",Toast.LENGTH_SHORT).show()
        }


        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile, MainPaymentFragment(),
                            "MainPaymentFragment"
                        )
                        commit()
                    }
                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentContractFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentContractFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}