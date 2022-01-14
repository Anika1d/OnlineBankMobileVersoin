package com.fefuproject.druzhbank.dirprofile.dircard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardBinding
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.util.CardType
import com.fefuproject.shared.account.util.Util
import java.util.*

@SuppressLint("NotifyDataSetChanged")
interface CardsActionListener {
    fun onCardDetails(card: CardModel) {

    }
}

class CardsAdapter(
    private val actionListener: CardsActionListener
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>(), View.OnClickListener {
    var cardsList = ArrayList<CardModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val card: CardModel = v.tag as CardModel
        actionListener.onCardDetails(card)
    }

    override fun getItemCount(): Int = cardsList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CardViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardsList[position]
        with(holder.binding) {
            holder.itemView.tag = card
            numdercard.text = card.number.take(4)+"*".repeat(8)+card.number.takeLast(4)
            valuecard.text = card.count + " руб."
            typecard.text = "Дебетовая карта"

            val typeCard = Util.getCardIssuer(cardNumber = card.number)
            when (typeCard) {
                CardType.MIR -> {
                    imagebank.setImageResource(R.drawable.ic_mir)
                }
                CardType.Mastercard -> {
                    imagebank.setImageResource(R.drawable.ic_mastercard)
                }
                CardType.VISA -> {
                    imagebank.setImageResource(R.drawable.ic_visa)
                }
            }
            if (card.is_blocked)
                root.setBackgroundColor(android.graphics.Color.DKGRAY)
            else root.setBackgroundColor(android.graphics.Color.WHITE)

        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCard(card: CardModel) {
        cardsList.add(card)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addListCard(card: List<CardModel>) {
        cardsList.addAll(card)
        notifyDataSetChanged()
    }

    class CardViewHolder(
        val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
