package com.fefuproject.druzhbank.dirhistoryallpayment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemHistoryPayBinding
import com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory.CardHistory
import com.fefuproject.druzhbank.dirprofile.dirpay.dirhistorypay.HistoryPayFragment
import java.util.logging.Filter
import java.util.logging.LogRecord

class AllHistoryPaymentAdapter :
    RecyclerView.Adapter<AllHistoryPaymentAdapter.AllHistoryPaymentHolder>() {

    val HistoryList = ArrayList<AllOperationHistory>()
    val HistoryListAll = ArrayList<AllOperationHistory>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllHistoryPaymentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_pay, parent, false)
        return AllHistoryPaymentHolder(view)
    }

    override fun onBindViewHolder(paymentHolder: AllHistoryPaymentHolder, position: Int) {
        paymentHolder.bind(HistoryList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return HistoryList.size
    }


    class AllHistoryPaymentHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemHistoryPayBinding.bind(v)

        @SuppressLint("SetTextI18n")
        fun bind(H: AllOperationHistory) = with(binding) {
            valueOperationPay.text = H.value.toString() + " рублей"
            nameOperationPay.text = H.name
            dateOperationPay.text = H.date

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addOperationHistory(H: AllOperationHistory) {
        HistoryList.add(H)
        HistoryListAll.add(H)
        notifyDataSetChanged()
    }

    fun addListOperationHistory(H: ArrayList<AllOperationHistory>) {
        HistoryList.addAll(H)
        HistoryListAll.addAll(H)
        notifyDataSetChanged()
    }

/*
    inner class Filter() : android.widget.Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<AllOperationHistory>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(HistoryListAll)
            } else {
                val filteredPattern: String = p0.toString().toLowerCase().trim()
                for (alh: AllOperationHistory in HistoryListAll) {
                    if (alh.name.toLowerCase().contains(filteredPattern)) {
                        filteredList.add(alh)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            filterResults.count = filteredList.size
            return filterResults
        }


        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults) {
            HistoryList.clear()
            HistoryList.addAll((p1.values as Collection<AllOperationHistory>))
            notifyDataSetChanged()
        }

    }

     fun getFilter(): ArrayList<AllOperationHistory> {
        return HistoryList
    }*/
}
