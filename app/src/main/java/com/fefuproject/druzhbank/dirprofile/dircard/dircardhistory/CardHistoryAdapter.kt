package com.fefuproject.druzhbank.dirprofile.dircard.dircardhistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentProfileMainBinding
import com.fefuproject.druzhbank.databinding.ItemCardHistoryBinding

class CardHistoryAdapter : RecyclerView.Adapter<CardHistoryAdapter.CardHistoryHolder>() {
    val сardHistoryList = ArrayList<CardHistory>()
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
        fun bind(cardH: CardHistory) = with(binding) {
                summaoperation.text=cardH.value.toString()+" рублей"
                operation.text=cardH.name
                dateoperationcard.text=cardH.date

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCardHistory(cardh: CardHistory) {
        сardHistoryList.add(cardh)
        notifyDataSetChanged()
    }
}