package com.fefuproject.druzhbank.historyallpayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.fefuproject.druzhbank.databinding.FragmentAllHistoryPaymentBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class AllHistoryPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAllHistoryPaymentBinding? = null
    private val binding get() = _binding!!
    private val adapterf = AllHistoryPaymentAdapter()

    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    @Inject
    lateinit var getAllHistoryInstrumentUseCase: GetAllInstrumentHistoryUseCase
    lateinit var AllH_list: List<HistoryInstrumentModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            AllH_list = getAllHistoryInstrumentUseCase.invoke(preferenceProvider.token!!,10)!!
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
        _binding = FragmentAllHistoryPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterf.addOperationHistoryList(AllH_list)
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