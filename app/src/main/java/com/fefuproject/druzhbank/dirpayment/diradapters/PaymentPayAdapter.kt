package com.fefuproject.druzhbank.dirpayment.diradapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.databinding.ItemPayImageBinding
import com.fefuproject.druzhbank.dirprofile.dircard.Cards
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays

class PaymentPayAdapter(
    private val actionListener: PaymentPaysActionListener
) : RecyclerView.Adapter<PaymentPayAdapter.PaymentPayHolder>(), View.OnClickListener {
    var paymentPayList = ArrayList<Pays>()
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

    override fun onBindViewHolder(holder: PaymentPayHolder, position: Int) {
        holder.itemView.tag = paymentPayList[position]
        with(holder.binding) {
            namePay.text = paymentPayList[position].namePay
            valuePay.text = paymentPayList[position].valuePay
            fullNumberPay.text = paymentPayList[position].numberPay
        }
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paymentPayList.size
    }

    class PaymentPayHolder(
        val binding: ItemPayImageBinding
    ) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun addPaymentPay(Pay: Pays) {
        paymentPayList.add(Pay)
        notifyDataSetChanged() //данные обновились, адаптер теперь перерисует
    }

    override fun onClick(v: View) {
        val p: Pays = v.tag as Pays
        actionListener.onPayDetails(p)
    }
}

interface PaymentPaysActionListener {
    fun onPayDetails(pay: Pays) {

    }
}