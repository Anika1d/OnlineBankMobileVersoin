package com.fefuproject.druzhbank.dirprofile.dircredit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemCreditBinding
import com.fefuproject.shared.account.domain.models.CreditModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreditsAdapter : RecyclerView.Adapter<CreditsAdapter.CreditsHolder>() {
    val сreditsList = ArrayList<CreditModel>()
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
        fun bind(credit:CreditModel) = with(binding) {
            typecredit.text = credit.name
            val currentDate = credit.payment_date
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateText = dateFormat.format(currentDate)
            datecreditpay.text = dateText.toString()
            valuecredit.text = credit.count
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addCreditList(creditsList: List<CreditModel>) {
        сreditsList.addAll(creditsList)
    }
}