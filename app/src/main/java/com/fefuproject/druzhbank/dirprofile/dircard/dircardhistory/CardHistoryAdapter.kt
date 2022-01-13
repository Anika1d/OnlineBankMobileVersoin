package com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import com.fefuproject.druzhbank.databinding.ItemCardHistoryBinding
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CardHistoryAdapter : RecyclerView.Adapter<CardHistoryAdapter.CardHistoryHolder>() {
    val сardHistoryList = ArrayList<HistoryInstrumentModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_history, parent, false)
        return CardHistoryHolder(view)
    }

    override fun onBindViewHolder(holder: CardHistoryHolder, position: Int) {
        holder.bind(cardH = сardHistoryList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return сardHistoryList.size
    }

    class CardHistoryHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemCardHistoryBinding.bind(v)
        @SuppressLint("SetTextI18n")
        fun bind(cardH:  HistoryInstrumentModel) = with(binding) {
                summaoperation.text=cardH.count+" руб."
                operation.text=cardH.pay_type
             val currentDate =cardH.date ///заполнение изменяемых данных
             val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
             val dateText = dateFormat.format(currentDate)
                dateoperationcard.text=dateText

        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addCardHistoryList(cardh: List<HistoryInstrumentModel>) {
        сardHistoryList.addAll(cardh)
        notifyDataSetChanged()
    }
}