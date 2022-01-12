package com.fefuproject.druzhbank.dirmoneytransfer.diradapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardImageBinding
import com.fefuproject.druzhbank.dirprofile.dircard.Cards


class  CardTransferAdapter: RecyclerView.Adapter< CardTransferAdapter.CardTransferHolder>() {
    val cardList = ArrayList<Cards>()

    class CardTransferHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemCardImageBinding.bind(v)
        fun bind(card: Cards) = with(binding) {
            typecard.text=card.typeCard
            numberCard.text=card.numberCard
            balansCard.text=card.valueCard
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CardTransferHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_image, parent, false)
        return CardTransferHolder(view)
    }

    override fun onBindViewHolder(holder:CardTransferHolder, position: Int) {
        holder.bind(card = cardList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return cardList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addCard(card: Cards){
        cardList.add(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }
}
