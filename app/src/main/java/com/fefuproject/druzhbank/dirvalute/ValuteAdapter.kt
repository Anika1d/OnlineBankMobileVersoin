package com.fefuproject.druzhbank.dirvalute

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemValuteBinding
import com.fefuproject.shared.account.domain.models.ValuteModel
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ValuteAdapter : RecyclerView.Adapter<ValuteAdapter.ValuteHolder>() {
    val valuteList = ArrayList<ValuteModel>()

    lateinit var parent1: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valute, parent, false)
        parent1 = parent
        return ValuteHolder(view)
    }

    override fun onBindViewHolder(holder: ValuteHolder, position: Int) {
        holder.bind(valute = valuteList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return valuteList.size
    }

    inner class ValuteHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemValuteBinding.bind(v)

        fun bind(valute: ValuteModel) = with(binding) {
            val nf: NumberFormat = NumberFormat.getInstance(Locale.FRANCE)
            var d: Double = nf.parse(valute.Value)?.toDouble()?.minus(2.01) ?: 1.0
            val drawableResourceId: Int = parent1.context.resources
                .getIdentifier(
                    if (valute.CharCode != "TRY") valute.CharCode.lowercase(Locale.getDefault()) else "try1",
                    "drawable",
                    parent1.context.packageName
                )
            imageFlag.setImageResource(drawableResourceId)//тут нужно понять, как обратится к конкретному флагу
            shortNameValute.text = valute.CharCode
            fullNameValute.text = valute.Name

            priceBuy.text = valute.Value

            priceResell.text = String.format("%.4f", d)
            /*      if (valute.isBuyUp) {//если курс вырос
                      arrowBuy.text = "\u2191"
                      arrowBuy.setTextColor(Color.GREEN)

                  } else {
                      arrowBuy.text = "\u2193"
                      arrowBuy.setTextColor(Color.RED)
                  }
                  if (valute.isResellUp) {
                      arrowResell.text = "\u2191"
                      arrowResell.setTextColor(Color.GREEN)

                  } else {
                      arrowResell.text = "\u2193"
                      arrowResell.setTextColor(Color.RED)
                  }*/
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addValute(valute: ValuteModel) {
        valuteList.add(valute)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addValuteList(valute: List<ValuteModel>) {
        valuteList.addAll(valute)
        notifyDataSetChanged()
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