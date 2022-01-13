package com.fefuproject.druzhbank.dirhistoryallpayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentAllHistoryPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllHistoryPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllHistoryPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAllHistoryPaymentBinding? = null
    private val binding get() = _binding!!
    private val adapterf = AllHistoryPaymentAdapter()
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
        _binding = FragmentAllHistoryPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterf.addOperationHistory(
            AllOperationHistory(
                name = "Перевод на карту",
                value = 2000.0,
                date = "11.12.21"
            )
        )
        adapterf.addOperationHistory(
            AllOperationHistory(
                name = "Перевод на карту",
                value = 2100.0,
                date = "11.12.21"
            )
        )
        binding.historyAllRc.adapter = adapterf
        binding.historyAllRc.addItemDecoration(CommonItemSpaceDecoration(15))
        binding.searchView.maxWidth = Int.MAX_VALUE
        binding.searchView.queryHint = "Поиск операции"
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {


                override fun onQueryTextSubmit(newText: String?): Boolean {
                    TODO("Not yet implemented")
                }


                override fun onQueryTextChange(newText: String?): Boolean {
               return false
                }

            })

    }
}