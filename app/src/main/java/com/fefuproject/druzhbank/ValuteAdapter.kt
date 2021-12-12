package com.fefuproject.druzhbank

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemValuteBinding


class ValuteAdapter : RecyclerView.Adapter<ValuteAdapter.ValuteHolder>() {
    val valuteList = ArrayList<Valute>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valute, parent, false)
        return ValuteHolder(view)
    }

    override fun onBindViewHolder(holder: ValuteHolder, position: Int) {
        holder.bind(valute = valuteList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return valuteList.size
    }

    class ValuteHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemValuteBinding.bind(v)
        fun bind(valute: Valute) = with(binding) {
            imageFlag.setImageResource(R.drawable.us)//тут нужно понять, как обратится к конкретному флагу
            shortNameValute.text=valute.short_name_valute
            fullNameValute.text=valute.long_name_valute
            priceBuy.text=valute.price_Buy.toString()
            priceResell.text=valute.price_Resell.toString()
            if (valute.isBuyUp){//если курс вырос
                arrowBuy.text="\u2191"
                arrowBuy.setTextColor(Color.GREEN)

            }
            else{
                arrowBuy.text="\u2193"
                arrowBuy.setTextColor(Color.RED)
            }
            if (valute.isResellUp){
                arrowResell.text="\u2191"
                arrowResell.setTextColor(Color.GREEN)

            }
            else{
                arrowResell.text="\u2193"
                arrowResell.setTextColor(Color.RED)
            }
        }
    }

    fun addValute(valute: Valute) {
        valuteList.add(valute)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }
}
/*
стрелка вверх
Номер в Юникоде для хмл
\u2191


стрелка вниз
Номер в Юникоде для хмл
\u2193
 */