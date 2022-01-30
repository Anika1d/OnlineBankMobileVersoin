package com.fefuproject.druzhbank.payment.adapters

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

interface PaymentCardsActionListener {
    fun onCardDetails(card: CardModel) {

    }
}

class PaymentCardAdapter(
    private val actionListener: PaymentCardsActionListener
) : RecyclerView.Adapter<PaymentCardAdapter.PaymentCardHolder>(), View.OnClickListener {
    var paymentCardList = ArrayList<CardModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardImageBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return PaymentCardHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentCardHolder, position: Int) {
        val card = paymentCardList[position]
        with(holder.binding) {
            holder.itemView.tag = card
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
            typecard.text = card.name
            balansCard.text = card.count + " руб."
            numberCard.text = card.number.take(4) + "*".repeat(8) + card.number.takeLast(4)
            if (card.is_blocked) blockedCard.setTextColor(android.graphics.Color.RED)
            else blockedCard.setTextColor(android.graphics.Color.TRANSPARENT)
        }
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paymentCardList.size
    }

    class PaymentCardHolder(val binding: ItemCardImageBinding) :
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun addCard(card: CardModel) {
        paymentCardList.add(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPaymentCardList(card: List<CardModel>) {
        paymentCardList.addAll(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    override fun onClick(p0: View) {
        val cards: CardModel = p0.tag as CardModel
        actionListener.onCardDetails(cards)
    }
}
