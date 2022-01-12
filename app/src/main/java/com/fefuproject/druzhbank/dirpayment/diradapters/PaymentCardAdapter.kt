package com.fefuproject.druzhbank.dirpayment.diradapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemCardImageBinding
import com.fefuproject.druzhbank.dirprofile.dircard.Cards

interface PaymentCardsActionListener {
    fun onCardDetails(card: Cards) {

    }
}

class PaymentCardAdapter(
    private val actionListener: PaymentCardsActionListener
) : RecyclerView.Adapter<PaymentCardAdapter.PaymentCardHolder>(), View.OnClickListener {
    var paymentCardList = ArrayList<Cards>()
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

    override fun onBindViewHolder(holder: PaymentCardHolder, position: Int) {
        val card = paymentCardList[position]
        with( holder.binding) {
            holder.itemView.tag=card
            typecard.text = card.typeCard
            numberCard.text = card.numberCard
            balansCard.text = card.valueCard
        }
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paymentCardList.size
    }

    class PaymentCardHolder(val binding: ItemCardImageBinding) :
        RecyclerView.ViewHolder(binding.root)



    @SuppressLint("NotifyDataSetChanged")
    fun addPaymentCard(card: Cards) {
        paymentCardList.add(card)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    override fun onClick(p0: View) {
        val cards: Cards = p0.tag as Cards
        actionListener.onCardDetails(cards)
    }
}
