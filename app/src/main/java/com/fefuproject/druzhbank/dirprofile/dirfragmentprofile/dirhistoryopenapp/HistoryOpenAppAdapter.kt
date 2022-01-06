package com.fefuproject.druzhbank.dirprofile.dirfragmentprofile.dirhistoryopenapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.ItemHistoryOpenApplicationBinding

class HistoryOpenAppAdapter : RecyclerView.Adapter<HistoryOpenAppAdapter.HistoryOpenAppHolder>() {

    val historyOpenAppList = ArrayList<HistoryOpenApp>()
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
        fun bind(historyOpenApp: HistoryOpenApp) = with(binding) {
                dateopenapp.text=historyOpenApp.date
                timeopenapp.text=historyOpenApp.time
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addHistoryOpenApp(historyOpenApp: HistoryOpenApp) {
        historyOpenAppList.add(historyOpenApp)
        notifyDataSetChanged()
    }
}
