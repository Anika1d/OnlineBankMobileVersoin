package com.fefuproject.druzhbank.dirprofile.dircredit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCreditBinding
class CreditsAdapter :RecyclerView.Adapter<CreditsAdapter.CreditsHolder>()
{
    val сreditsList = ArrayList<Credits>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_credit, parent, false)
        return CreditsHolder(view)
    }

    override fun onBindViewHolder(holder: CreditsHolder, position: Int) {
        holder.bind(credit = сreditsList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return сreditsList.size
    }

    class CreditsHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemCreditBinding.bind(v)
        fun bind(credit: Credits) = with(binding) {
            typecredit.text=credit.nameCredit
            datecreditpay.text=credit.dateCredit
            valuecredit.text=credit.valueCredit
        }

    }
}