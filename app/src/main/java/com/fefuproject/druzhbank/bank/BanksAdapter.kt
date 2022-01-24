package com.fefuproject.druzhbank.bank

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemBankBinding
import com.fefuproject.shared.account.domain.models.BankomatModel

class BanksAdapter : RecyclerView.Adapter<BanksAdapter.BanksHolder>() {

    val banksList = ArrayList<BankomatModel>()
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

        @SuppressLint("SetTextI18n")
        fun bind(bank: BankomatModel) = with(binding) {
            if (bank.is_atm) {
                whoHouse.text = "Банкомант"
            } else {
                whoHouse.text = "Отделение"
            }
            timeDate.text =
                bank.time_start.substring(0,5) + "-" +
                        bank.time_end.substring(0,5)
            if (bank.is_working == true) {
                isWorked.text = "Работает"
                isWorked.setTextColor(Color.GREEN)
            } else {
                isWorked.text = "Закрыто"
                isWorked.setTextColor(Color.RED)
            }
            Adress.text = bank.adress
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBank(bank: BankomatModel) {
        banksList.add(bank)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBankList(bank: List<BankomatModel>) {
        banksList.addAll(bank)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }
}