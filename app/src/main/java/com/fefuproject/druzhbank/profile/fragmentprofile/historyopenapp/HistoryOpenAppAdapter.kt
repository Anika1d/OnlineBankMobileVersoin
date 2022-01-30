package com.fefuproject.druzhbank.profile.fragmentprofile.historyopenapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemHistoryOpenApplicationBinding
import com.fefuproject.shared.account.domain.models.LoginHistoryModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryOpenAppAdapter : RecyclerView.Adapter<HistoryOpenAppAdapter.HistoryOpenAppHolder>() {

    val historyOpenAppList = ArrayList<LoginHistoryModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOpenAppHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_open_application, parent, false)
        return HistoryOpenAppHolder(view)
    }

    override fun onBindViewHolder(holder:  HistoryOpenAppHolder, position: Int) {
        holder.bind(historyOpenApp = historyOpenAppList[position])
    }

    override fun getItemCount(): Int { ///возрашает кол-во елементов
        return historyOpenAppList.size
    }

    class HistoryOpenAppHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = ItemHistoryOpenApplicationBinding.bind(v)
        fun bind(historyOpenApp:LoginHistoryModel) = with(binding) {
            val currentDate = historyOpenApp.date_visit
            val dateFormatDate: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateTextDate = dateFormatDate.format(currentDate)
                dateopenapp.text=dateTextDate.toString()
            val dateFormatTime: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val dateTextTime = dateFormatTime.format(currentDate)
                timeopenapp.text=dateTextTime
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addHistoryOpenAppList(historyList: List<LoginHistoryModel>) {
        historyOpenAppList.addAll(historyList)
        notifyDataSetChanged()
    }
}
