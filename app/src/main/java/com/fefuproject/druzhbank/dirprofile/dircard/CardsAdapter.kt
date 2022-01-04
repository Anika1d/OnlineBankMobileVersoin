package com.fefuproject.druzhbank.dirprofile.dircard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCardBinding

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.CardsHolder>() {
    val сardsList = ArrayList<Cards>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardsHolder(view)
    }

    override fun onBindViewHolder(holder: CardsHolder, position: Int) {
        holder.bind(card = сardsList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return сardsList.size
    }

    class CardsHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemCardBinding.bind(v)
        fun bind(card: Cards) = with(binding) {
         typecard.text=card.typeCard
            valuecard.text=card.valueCard
            /////imagebank сделать
            numdercard.text=card.numberCard

            }

    }
}