package com.example.onlinebank

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinebank.databinding.ItemBankBinding

class BanksAdapter : RecyclerView.Adapter<BanksAdapter.BanksHolder>() {
    val banksList = ArrayList<Banks>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bank, parent, false)
        return BanksHolder(view)
    }

    override fun onBindViewHolder(holder: BanksHolder, position: Int) {
        holder.bind(bank = banksList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return banksList.size
    }

    class BanksHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemBankBinding.bind(v)
        fun bind(bank: Banks) = with(binding) {
            whoHouse.text=bank.Department
            timeDate.text = bank.time_work
            if (bank.isWorked) {
                isWorked.text = "Работает"
                isWorked.setTextColor(Color.GREEN)
            } else {
                isWorked.text = "Закрыто"
                isWorked.setTextColor(Color.RED)
            }
            Adress.text = bank.street
        }
    }
    fun addBank(bank: Banks){
        banksList.add(bank)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }
}