package com.fefuproject.druzhbank.dirmoneytransfer.diradapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardImageBinding
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.util.CardType
import com.fefuproject.shared.account.util.Util


class CardTransferAdapter : RecyclerView.Adapter<CardTransferAdapter.CardTransferHolder>() {
    val cardList = ArrayList<CardModel>()

    class CardTransferHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemCardImageBinding.bind(v)

        @SuppressLint("SetTextI18n")
        fun bind(card: CardModel) = with(binding) {
            val typeCard = Util.getCardIssuer(cardNumber = card.number)
            when (typeCard) {
                CardType.MIR -> {
                    cardImage.setImageResource(R.drawable.ic_mir)
                }
                CardType.Mastercard -> {
                    cardImage.setImageResource(R.drawable.ic_mastercard)
                }
                CardType.VISA -> {
                    cardImage.setImageResource(R.drawable.ic_visa)
                }
            }
            balansCard.text = card.count+ " руб."
            numberCard.text = card.number[0].toString() + card.number[1].toString() +
                    card.number[2].toString() + card.number[3].toString() + "****" +
                    card.number[card.number.length - 4].toString() + card.number[card.number.length - 3].toString() +
                    card.number[card.number.length - 2].toString() + card.number[card.number.length - 1].toString()
            if (card.is_blocked) {
                blockedCard.setTextColor(android.graphics.Color.RED)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTransferHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_image, parent, false)
        return CardTransferHolder(view)
    }

    override fun onBindViewHolder(holder: CardTransferHolder, position: Int) {
        holder.bind(card = cardList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return cardList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addCard(card: CardModel) {
        cardList.add(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addListCard(card: List<CardModel>) {
        cardList.addAll(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }
}
