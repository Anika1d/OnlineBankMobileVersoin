package com.fefuproject.druzhbank.dirprofile.dircard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemCardBinding
import java.util.*

@SuppressLint("NotifyDataSetChanged")
interface CardsActionListener {
    fun onCardDetails(card: Cards) {

    }
}

class CardsAdapter(
    private val actionListener: CardsActionListener
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>(), View.OnClickListener {
    var cardsList = ArrayList<Cards>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val card: Cards = v.tag as Cards
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
            numdercard.text = card.numberCard
            valuecard.text = card.valueCard
            typecard.text = card.typeCard
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    fun addCard(card: Cards){
        cardsList.add(card)
        notifyDataSetChanged()
    }
    class CardViewHolder(
        val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
