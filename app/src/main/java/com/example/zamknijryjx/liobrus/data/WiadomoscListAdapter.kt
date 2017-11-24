package com.example.zamknijryjx.liobrus.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zamknijryjx.liobrus.R
import com.example.zamknijryjx.liobrus.model.Wiadomosc

/**
 * Created by zamknijryjx on 12.11.17.
 */

class WiadomoscListAdapter(private val list: ArrayList<Wiadomosc>,
                            private val context: Context): RecyclerView.Adapter<WiadomoscListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.list_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null) {
            holder.bindView(list[position])
        }
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var przedmiot = itemView.findViewById<TextView>(R.id.przedmiot)
        var opis = itemView.findViewById<TextView>(R.id.opis)
        var data = itemView.findViewById<TextView>(R.id.data)

        fun bindView(spr: Wiadomosc) {
            przedmiot.text = spr.nadawca
            opis.text = spr.temat
            data.text =spr.data
        }

    }
}