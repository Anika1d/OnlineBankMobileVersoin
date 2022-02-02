package com.fefuproject.druzhbank.bank

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemBankBinding
import com.fefuproject.druzhbank.databinding.ItemCardBinding
import com.fefuproject.shared.account.domain.models.BankomatModel
import com.fefuproject.shared.account.domain.models.CardModel

class BanksAdapter (
  private val actionListener: BankActionListener
        ): RecyclerView.Adapter<BanksAdapter.BanksHolder>(), View.OnClickListener {

    val banksList = ArrayList<BankomatModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBankBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return BanksHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BanksHolder, position: Int) {
        val bank = banksList[position]
        holder.itemView.tag = bank
        with(holder.binding) {
            if (bank.is_atm) {
                whoHouse.text = "Банкомант"
            } else {
                whoHouse.text = "Отделение"
            }
            timeDate.text =
                bank.time_start.substring(0, 5) + "-" +
                        bank.time_end.substring(0, 5)
            if (bank.is_working == true) {
                isWorked.text = "Работает"
                isWorked.setTextColor(android.graphics.Color.GREEN)
            } else {
                isWorked.text = "Закрыто"
                isWorked.setTextColor(android.graphics.Color.RED)
            }
            Adress.text = bank.adress
        }
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return banksList.size
    }

    class BanksHolder(
        val binding: ItemBankBinding
    ) : RecyclerView.ViewHolder(binding.root)


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

    override fun onClick(p0: View) {
        val bank: BankomatModel = p0.tag as BankomatModel
        actionListener.onBankInMap(bank = bank)
    }
}
interface BankActionListener {
    fun onBankInMap(bank: BankomatModel) {

    }
}