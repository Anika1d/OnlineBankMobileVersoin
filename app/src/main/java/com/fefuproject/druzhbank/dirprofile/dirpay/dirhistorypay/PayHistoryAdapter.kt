package com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardHistoryBinding
import com.fefuproject.druzhbank.databinding.ItemHistoryPayBinding
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistory

class  PayHistoryAdapter : RecyclerView.Adapter< PayHistoryAdapter.PayHistoryHolder>() {
    val payHistoryList = ArrayList<PayHistory>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayHistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_pay, parent, false)
        return PayHistoryHolder(view)
    }

    override fun onBindViewHolder(holder: PayHistoryHolder, position: Int) {
        holder.bind( payHistoryList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return payHistoryList.size
    }

    class PayHistoryHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemHistoryPayBinding.bind(v)
        @SuppressLint("SetTextI18n")
        fun bind(payHistory: PayHistory) =
            with(binding)
            {
                valueOperationPay.text = payHistory.valueOperation
                dateOperationPay.text = payHistory.dateOperation
                nameOperationPay.text = payHistory.nameOperation

            }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPayHistory(payHistory: PayHistory) {
        payHistoryList.add(payHistory)
        notifyDataSetChanged()
    }
}