package com.fefuproject.druzhbank.dirprofile.dirpay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemPayBinding

interface PaysActionListener {
    fun onPayDetails(pay: Pays) {

    }
}

class PaysAdapter(
    private val actionListener: PaysActionListener
) : RecyclerView.Adapter<PaysAdapter.PaysHolder>(), View.OnClickListener {
    val paysList = ArrayList<Pays>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPayBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return PaysHolder(binding)
    }

    override fun onBindViewHolder(holder: PaysHolder, position: Int) {
        val pay = paysList[position]
        with(holder.binding) {
            holder.itemView.tag = pay
            valuepay.text = pay.valuePay
            namepay.text = pay.namePay
            numberpay.text = pay.numberPay
        }

    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paysList.size
    }

    class PaysHolder(val binding: ItemPayBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun addPay(pay: Pays) {
        paysList.add(pay)
        notifyDataSetChanged()
    }

    override fun onClick(p0: View) {
        val pays: Pays = p0.tag as Pays
        actionListener.onPayDetails(pays)
    }
}
