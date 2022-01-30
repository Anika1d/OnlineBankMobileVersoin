package com.fefuproject.druzhbank.profile.fragmentprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class ProfileMainFragment : Fragment() {
    private val viewModel:ProfileMainViewModel by viewModels()
    private var _binding: FragmentProfileMainBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        binding.shimmerCard.startShimmer()
        binding.shimmerCredits.startShimmer()
        binding.shimmerPay.startShimmer()
        viewModel.initData(binding,this)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        activity?.onBackPressedDispatcher?.addCallback(
//            this@ProfileMainFragment,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    if (backButtonCount >= 1) {
//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.addCategory(Intent.CATEGORY_HOME)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    } else {
//                       makeText(
//                            this@ProfileMainFragment.context,
//                            "Нажмите еще раз,чтобы выйти из приложения.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        backButtonCount++
//                    }
//                }
//            })

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