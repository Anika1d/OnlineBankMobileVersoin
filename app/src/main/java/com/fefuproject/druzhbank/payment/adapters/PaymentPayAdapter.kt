package com.fefuproject.druzhbank.payment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemPayImageBinding
import com.fefuproject.shared.account.domain.models.CheckModel

class PaymentPayAdapter(
    private val actionListener: PaymentPaysActionListener
) : RecyclerView.Adapter<PaymentPayAdapter.PaymentPayHolder>(), View.OnClickListener {
    var paymentPayList = ArrayList<CheckModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentPayHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPayImageBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return PaymentPayHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentPayHolder, position: Int) {
        holder.itemView.tag = paymentPayList[position]
        with(holder.binding) {
            namePay.text = paymentPayList[position].name
            valuePay.text = paymentPayList[position].count+" руб."
            fullNumberPay.text = "*".repeat(9) + paymentPayList[position].number.takeLast(4)
        }
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paymentPayList.size
    }

    class PaymentPayHolder(
        val binding: ItemPayImageBinding
    ) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun addPaymentPayList(checkList: List<CheckModel>) {
        paymentPayList.addAll(checkList)
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val p: CheckModel = v.tag as CheckModel
        actionListener.onPayDetails(p)
    }


}

interface PaymentPaysActionListener {
    fun onPayDetails(pay: CheckModel) {

    }
}