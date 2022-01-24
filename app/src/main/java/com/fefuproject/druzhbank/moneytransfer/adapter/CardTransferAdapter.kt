package com.fefuproject.druzhbank.moneytransfer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardImageBinding
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.util.CardType
import com.fefuproject.shared.account.util.Util

interface CardsActionListenerT {
    fun onCardDetails(card: CardModel) {

    }
}

class CardTransferAdapter(
    val actionListener: CardsActionListenerT
) : RecyclerView.Adapter<CardTransferAdapter.CardTransferHolder>(), View.OnClickListener {
    val cardList = ArrayList<CardModel>()

    override fun onClick(v: View) {
        val card: CardModel = v.tag as CardModel
        actionListener.onCardDetails(card)
    }

    class CardTransferHolder(
        val binding: ItemCardImageBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTransferHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardImageBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CardTransferHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardTransferHolder, position: Int) {
        val card = cardList[position]
        with(holder.binding) {
            holder.itemView.tag = card
            numberCard.text = card.number.take(4) + "*".repeat(8) + card.number.takeLast(4)
            balansCard.text = card.count + " руб."
            typecard.text = "Дебетовая карта"

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
            if (card.is_blocked)
                root.setBackgroundColor(android.graphics.Color.DKGRAY)
            else root.setBackgroundColor(android.graphics.Color.WHITE)

        }
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
