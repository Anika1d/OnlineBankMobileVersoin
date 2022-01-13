package com.fefuproject.druzhbank.dirhistoryallpayment

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

class AllHistoryPaymentAdapter :
    RecyclerView.Adapter<AllHistoryPaymentAdapter.AllHistoryPaymentHolder>() {

    val HistoryList = ArrayList<HistoryInstrumentModel>()
    val HistoryListAll = ArrayList<HistoryInstrumentModel>()
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
        fun bind(H: HistoryInstrumentModel) = with(binding) {
            valueOperationPay.text = H.count + " руб."
            val currentDate =H.date ///заполнение изменяемых данных
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateText = dateFormat.format(currentDate)
            dateOperationPay.text = dateText
            nameOperationPay.text=H.pay_type

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addOperationHistoryList(H: List<HistoryInstrumentModel>) {
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
