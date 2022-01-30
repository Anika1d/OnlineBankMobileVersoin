package com.fefuproject.druzhbank.profile.pay.historypay

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemHistoryPayBinding
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class  PayHistoryAdapter : RecyclerView.Adapter< PayHistoryAdapter.PayHistoryHolder>() {
    val payHistoryList = ArrayList<HistoryInstrumentModel>()
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
        fun bind(payHistory: HistoryInstrumentModel) =
            with(binding)
            {
                valueOperationPay.text = payHistory.count
                val currentDate = payHistory.date
                val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val dateText = dateFormat.format(currentDate)
                dateOperationPay.text = dateText
                nameOperationPay.text = payHistory.pay_type

            }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPayHistoryList(payHistory: List<HistoryInstrumentModel>) {
        payHistoryList.addAll(payHistory)
        notifyDataSetChanged()
    }
}