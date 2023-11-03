package com.ananda.casheer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ananda.casheer.database.CafeDatabase
import com.ananda.casheer.database.Transaksi
import com.example.casheer.R


class TransaksiAdapter(var items: List<Transaksi>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNama: TextView = view.findViewById(R.id.namaPelangganList)
        val textTanggal: TextView = view.findViewById(R.id.tglList)
        val textStatus: TextView = view.findViewById(R.id.statusList)
        val textMeja: TextView = view.findViewById(R.id.mejaList)
        val relative: RelativeLayout = view.findViewById(R.id.relative)
    }

    lateinit var db: CafeDatabase
    var onHolderClick: ((Transaksi) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        db = CafeDatabase.getInstance(parent.context)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaksi_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = items[position]
        holder.textNama.text = transaksi.nama_pelanggan
        holder.textTanggal.text = transaksi.tgl_transaksi
        holder.textStatus.text = transaksi.status

        val meja = db.cafeDao().getMeja(transaksi.id_meja)
        holder.textMeja.text = meja?.nomor_meja ?: "N/A"

        holder.relative.setOnClickListener {
            onHolderClick?.invoke(transaksi)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): Transaksi {
        return items[position]
    }
}
