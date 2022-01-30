package com.fefuproject.druzhbank.profile.pay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemPayBinding
import com.fefuproject.shared.account.domain.models.CheckModel

interface PaysActionListener {
    fun onPayDetails(pay:  CheckModel) {

    }
}

class PaysAdapter(
    private val actionListener: PaysActionListener
) : RecyclerView.Adapter<PaysAdapter.PaysHolder>(), View.OnClickListener {
    val paysList = ArrayList<CheckModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPayBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return PaysHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaysHolder, position: Int) {
        val pay = paysList[position]
        with(holder.binding) {
            holder.itemView.tag = pay
            valuepay.text = pay.count
            namepay.text = pay.name
            numberpay.text = "*".repeat(9)+pay.number.takeLast(4)
        }

    }
    override fun onClick(p0: View) {
        val pays: CheckModel = p0.tag as  CheckModel
        actionListener.onPayDetails(pays)
    }
    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paysList.size
    }

    class PaysHolder(val binding: ItemPayBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun addPayList(l:List<CheckModel>) {
        paysList.addAll(l)
        notifyDataSetChanged()
    }
}
