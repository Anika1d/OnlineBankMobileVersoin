package com.fefuproject.druzhbank.dirprofile.dirpay

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class PayImageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val paysList = ArrayList<Pays>()
   // val p=pos ,pos: Int
    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return paysList.size
    }

    override fun createFragment(position: Int): Fragment {
        return PayImageFragment(paysList[position])
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addPay(pay: Pays) {
        paysList.add(pay)
        notifyDataSetChanged()
    }

}