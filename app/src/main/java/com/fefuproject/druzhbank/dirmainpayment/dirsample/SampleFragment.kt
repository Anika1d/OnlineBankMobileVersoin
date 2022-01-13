package com.fefuproject.druzhbank.dirmainpayment.dirsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentSampleBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.models.Categories

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SampleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!


    private val adapter = SampleAdapter(
        object : SampleActionListener {
            override fun OnSampleDetails(categories: Categories) {
                makeText(
                    this@SampleFragment.context,
                    "типо тут две кнопки при свайпе влево",
                    Toast.LENGTH_SHORT
                )
            }
        }
    )
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
        _binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleViewCategoriesVertical.adapter = adapter
        binding.recycleViewCategoriesVertical.addItemDecoration(
            CommonItemSpaceDecoration
                (15)
        )
        with(adapter) {
            addCat(categories)
            addCat(categories2)
            addCat(categories3)
            addCat(categories4)
        }
    }
}