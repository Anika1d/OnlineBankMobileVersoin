package com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.diradapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemCategoriesRectBinding
import com.fefuproject.druzhbank.dirmainpayment.dirpaymentcontract.models.Categories
import com.fefuproject.shared.account.domain.models.CategoryModel

interface CategoriesActionListener {
   fun OnCategoriesDetails(categories: CategoryModel){

    }
}

class CategoriesAdapterVertical(
  private val actionListener: CategoriesActionListener
): RecyclerView.Adapter<CategoriesAdapterVertical.CategoriesHolder>(), View.OnClickListener {
   var categoriesList= ArrayList<CategoryModel>()
       @SuppressLint("NotifyDataSetChanged")
       set(value) {
           field = value
           notifyDataSetChanged()

       }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemCategoriesRectBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return CategoriesHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onClick(p0: View) {
       val categories:CategoryModel = p0.tag as CategoryModel
        actionListener.OnCategoriesDetails(categories = categories)
    }
    class CategoriesHolder(
        val binding:ItemCategoriesRectBinding):RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.binding.nameCategories.text=categoriesList[position].name
        holder.itemView.tag=categoriesList[position]
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addCatList(sampleList: List<CategoryModel>) {
        categoriesList.addAll(sampleList)
        notifyDataSetChanged()
    }
}