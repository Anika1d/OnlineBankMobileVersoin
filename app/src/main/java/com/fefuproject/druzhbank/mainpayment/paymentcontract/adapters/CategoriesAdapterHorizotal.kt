package com.fefuproject.druzhbank.mainpayment.paymentcontract.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemCategoriesCircleBinding
import com.fefuproject.shared.account.domain.models.CategoryModel
import com.fefuproject.shared.account.domain.models.TemplateModel

interface CategoriesActionListenerH {
    fun OnCategoriesDetails(categories:TemplateModel){

    }
}

class CategoriesAdapterHorizotal(
    private val actionListener: CategoriesActionListenerH
) : RecyclerView.Adapter< CategoriesAdapterHorizotal.CategoriesHolder>(), View.OnClickListener {
    var categoriesList = ArrayList<TemplateModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =ItemCategoriesCircleBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CategoriesHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onClick(p0: View) {
        val categories: TemplateModel = p0.tag as TemplateModel
        actionListener.OnCategoriesDetails(categories)
    }

    class CategoriesHolder(
        val binding: ItemCategoriesCircleBinding
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.binding.nameCategories.text = categoriesList[position].name
        holder.itemView.tag=categoriesList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCatList(sampleList: List<TemplateModel>) {
        categoriesList.addAll(sampleList)
        notifyDataSetChanged()
    }
}