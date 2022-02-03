package com.fefuproject.druzhbank.mainpayment.sample

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentSampleBinding
import com.fefuproject.druzhbank.decoration.CommonItemSpaceDecoration
import com.fefuproject.druzhbank.decoration.RecyclerViewSwipeDecorator
import com.fefuproject.druzhbank.di.PreferenceProvider
import com.fefuproject.druzhbank.mainpayment.paymentcontract.PaymentContractFragment
import com.fefuproject.shared.account.domain.models.TemplateModel
import com.fefuproject.shared.account.domain.usecase.DeleteTemplatesByIdUseCase
import com.fefuproject.shared.account.domain.usecase.GetTemplatesUseCase
import com.fefuproject.shared.account.domain.usecase.SetTemplateUseCase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SampleViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val getTemplatesUseCase: GetTemplatesUseCase,
    private val deleteTemplatesByIdUseCase: DeleteTemplatesByIdUseCase,
    private val setTemplateUseCase: SetTemplateUseCase,
) : ViewModel() {

    private val adapter = SampleAdapter(
        object : SampleActionListener {
            @SuppressLint("UseRequireInsteadOfGet")
            override fun OnSampleDetails(categories: TemplateModel) {
                val bundle = Bundle()
                bundle.putString("templates", categories.id.toString())
                val fr = PaymentContractFragment()
                fr.arguments = bundle
                sampleFragment.activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragmentContainerViewProfile,
                        fr, "PaymentContractFragment"
                    )
                    commit()
                }
            }
        }
    )
    private lateinit var sample_list: List<TemplateModel>
    private lateinit var sampleFragment: SampleFragment
    private lateinit var binding: FragmentSampleBinding
    fun initData(_binding: FragmentSampleBinding, _sampleFragment: SampleFragment) {
        binding = _binding
        sampleFragment = _sampleFragment
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModelScope.launch {
                        val tmp_sample = adapter.categoriesList[viewHolder.adapterPosition]
                        deleteTemplatesByIdUseCase(preferenceProvider.token!!, tmp_sample.id)
                        val tmp_sample_position = viewHolder.adapterPosition
                        adapter.categoriesList.removeAt(viewHolder.adapterPosition)
                        adapter.notifyDataSetChanged()
                        Snackbar.make(
                            binding.recycleViewCategoriesVertical,
                            "Идет удаление",
                            Snackbar.LENGTH_LONG
                        ).setAction(
                            "Отменить", object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                    adapter.addCat(tmp_sample_position, tmp_sample)
                                    launch {
                                        setTemplateUseCase.invoke(
                                            preferenceProvider.token!!,
                                            tmp_sample.source,
                                            tmp_sample.dest,
                                            tmp_sample.source_type,
                                            tmp_sample.dest_type,
                                            tmp_sample.name,
                                            tmp_sample.sum
                                        )
                                    }
                                }
                            }

                        ).show()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                sampleFragment.requireContext(),
                                R.color.toup
                            )
                        )
                        .addSwipeLeftLabel("Удаление")
                        .addActionIcon(R.drawable.ic_trashcan)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            })
        viewModelScope.launch {
            mIth.attachToRecyclerView(binding.recycleViewCategoriesVertical)
            sample_list = getTemplatesUseCase.invoke(preferenceProvider.token!!)!!
            binding.recycleViewCategoriesVertical.adapter = adapter
            binding.recycleViewCategoriesVertical.addItemDecoration(CommonItemSpaceDecoration(15))
            adapter.addCatList(sample_list)
        }
    }
}