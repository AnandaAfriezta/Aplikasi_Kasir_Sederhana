package com.ananda.casheer.view.list

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ananda.casheer.SwipeGesture
import com.ananda.casheer.adapter.TransaksiAdapter
import com.ananda.casheer.database.CafeDatabase
import com.ananda.casheer.database.Transaksi
import com.ananda.casheer.view.edit.EditTransaksiActivity
import com.ananda.casheer.view.main.MainActivity2
import com.example.casheer.R


class ListTransaksiActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var adapter: TransaksiAdapter
    lateinit var db: CafeDatabase
    lateinit var btnBackLeft: ImageButton

    private var listTransaksi = arrayListOf<Transaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_transaksi)
        supportActionBar?.hide()

        recycler = findViewById(R.id.recyclerTransaksi)
        db = CafeDatabase.getInstance(applicationContext)
        btnBackLeft = findViewById(R.id.btn_backLeft)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = TransaksiAdapter(listTransaksi)
        recycler.adapter = adapter
        adapter.onHolderClick = {
            val moveIntent = Intent(this@ListTransaksiActivity, ListDetailTransaksiActivity::class.java)
            moveIntent.putExtra("id_transaksi", it.id_transaksi)
            startActivity(moveIntent)
        }
        btnBackLeft.setOnClickListener {
            val moveIntent = Intent(this@ListTransaksiActivity, MainActivity2::class.java)
            finish()

        }

        swipeToGesture(recycler)
    }

    override fun onResume() {
        super.onResume()
        getTransaksi()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getTransaksi(){
        listTransaksi.clear()
        listTransaksi.addAll(db.cafeDao().getAllTransaksi())
        adapter.notifyDataSetChanged()
    }

    private fun swipeToGesture(itemRv: RecyclerView){
        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try{
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            var adapter: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            db.cafeDao().deleteTransaksi(adapter.getItem(position))
                            adapter.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                        ItemTouchHelper.RIGHT -> {
                            val moveIntent = Intent(this@ListTransaksiActivity, EditTransaksiActivity::class.java)
                            var adapter: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            var transaksi = adapter.getItem(position)
                            moveIntent.putExtra("nama", transaksi.nama_pelanggan)
                            moveIntent.putExtra("nomormeja", transaksi.id_meja)
                            moveIntent.putExtra("ID", transaksi.id_transaksi)
                            startActivity(moveIntent)
                        }
                    }
                }
                catch (e: Exception){
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}