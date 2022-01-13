package com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentHistoryPayBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirprofile.dirpay.PayFragment
import com.fefuproject.shared.account.domain.models.CheckModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetCheckHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.GetChecksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
@AndroidEntryPoint
class HistoryPayFragment(val checkModel: CheckModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binging: FragmentHistoryPayBinding? = null
    private var adapter = PayHistoryAdapter()
    private val binding get() = _binging!!
    lateinit var payHistoryList: List<HistoryInstrumentModel>

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getCheckHistoryUseCase: GetCheckHistoryUseCase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runBlocking {
            payHistoryList =
                getCheckHistoryUseCase.invoke(checkModel.number, preferenceProvider.token!!, 1)!!
        }
        _binging = FragmentHistoryPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LinearLayoutManager(this@HistoryPayFragment.context).also {
            binding.recycleViewHistoryPay.layoutManager = it
        }
        adapter.addPayHistoryList(payHistory = payHistoryList)

        binding.recycleViewHistoryPay.adapter = adapter
        activity?.onBackPressedDispatcher?.addCallback(
            this@HistoryPayFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            PayFragment(checkModel = checkModel),
                            "PayFragment"
                        )
                        commit()
                    }
                }
            })
    }

}