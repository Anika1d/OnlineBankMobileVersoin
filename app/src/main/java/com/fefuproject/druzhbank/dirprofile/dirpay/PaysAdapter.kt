package com.fefuproject.druzhbank.dirprofile.dirpay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemPayBinding

class PaysAdapter : RecyclerView.Adapter<PaysAdapter.PaysHolder>() {
    val paysList = ArrayList<Pays>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pay, parent, false)
        return PaysHolder(view)
    }

    override fun onBindViewHolder(holder: PaysHolder, position: Int) {
        holder.bind(pay = paysList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paysList.size
    }

    class PaysHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemPayBinding.bind(v)
        fun bind(pay: Pays) = with(binding) {
            namepay.text = pay.namePay
            numberpay.text = pay.numberPay
            valuepay.text = pay.valuePay
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPay(pay: Pays) {
        paysList.add(pay)
        notifyDataSetChanged()
    }
}