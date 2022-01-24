package com.fefuproject.druzhbank.dirprofile.fragmentprofile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentHistoryOpenApplicationBinding
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.dirprofile.fragmentprofile.historyopenapp.HistoryOpenAppAdapter
import com.fefuproject.shared.account.domain.models.LoginHistoryModel
import com.fefuproject.shared.account.domain.usecase.GetLoginHistoryUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryOpenApplicationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HistoryOpenApplicationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHistoryOpenApplicationBinding? = null
    private val binding get() = _binding!!
    private val adapter = HistoryOpenAppAdapter()


    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    @Inject
    lateinit var getLoginHistoryUseCase: GetLoginHistoryUseCase

    lateinit var history_list:List<LoginHistoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        runBlocking {history_list=getLoginHistoryUseCase.invoke(preferenceProvider.token!!)!!  }
    }


    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryOpenApplicationBinding.inflate(inflater, container, false)
        adapter.addHistoryOpenAppList(history_list)
        binding.recycleViewHistoryOpenApp.adapter = adapter
        LinearLayoutManager(this@HistoryOpenApplicationFragment.context).also {
            binding.recycleViewHistoryOpenApp.layoutManager = it
        }


        activity?.onBackPressedDispatcher?.addCallback(
            this@HistoryOpenApplicationFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.fragmentContainerViewProfile,
                            ProfileMainFragment(),
                            "ProfileMainFragment"
                        )
                        commit()
                    }
                }
            })
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryOpenApplicationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryOpenApplicationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}