package com.fefuproject.druzhbank.mainpayment.sample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemCategoriesRectBinding
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.TemplateModel


interface SampleActionListener {
    fun OnSampleDetails(categories:TemplateModel) {

    }
}

class SampleAdapter(
    private val actionListener: SampleActionListener
) : RecyclerView.Adapter<SampleAdapter.SampleHolder>(), View.OnClickListener {
    var categoriesList = ArrayList<TemplateModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesRectBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return SampleHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onClick(p0: View) {
        val categories: TemplateModel = p0.tag as TemplateModel
        actionListener.OnSampleDetails(categories = categories)
    }

    class SampleHolder(
        val binding: ItemCategoriesRectBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.binding.nameCategories.text = categoriesList[position].name
        holder.itemView.tag = categoriesList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCatList(sampleList: List<TemplateModel>) {
        categoriesList.addAll(sampleList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCat(position: Int, sample: TemplateModel) {
        categoriesList.add(position, sample)
        notifyDataSetChanged()
    }
}