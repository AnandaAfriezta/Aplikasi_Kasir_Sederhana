package com.ananda.casheer.view.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.ananda.casheer.database.CafeDatabase
import com.example.casheer.R

class EditTransaksiActivity : AppCompatActivity() {
    private lateinit var inputNamaPelangganEditText: EditText
    private lateinit var spinnerMeja: Spinner
    private lateinit var simpanButton: Button
    private lateinit var dibayarCheckBox: CheckBox
    private var nomorMeja: Int = 0
    private lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaksi)
        supportActionBar?.hide()

        inputNamaPelangganEditText = findViewById(R.id.namaPelanggan)
        spinnerMeja = findViewById(R.id.spinnerMeja)
        simpanButton = findViewById(R.id.simpan)
        dibayarCheckBox = findViewById(R.id.dibayar)

        db = CafeDatabase.getInstance(applicationContext)
        setValuesToViews()
        setDataSpinner()

        val id_transaksi: Int = intent.getIntExtra("ID", 0)

        simpanButton.setOnClickListener {
            val status = if (dibayarCheckBox.isChecked) "Dibayar" else "Belum Dibayar"

            if (inputNamaPelangganEditText.text.isNotEmpty()) {
                val idMeja = db.cafeDao().getIdMejaFromNama(spinnerMeja.selectedItem.toString())
                db.cafeDao().updateTransaksi(
                    inputNamaPelangganEditText.text.toString(),
                    idMeja,
                    status,
                    id_transaksi
                )
                finish()
            } else {
                Toast.makeText(applicationContext, "Nama pelanggan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDataSpinner() {
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, db.cafeDao().getAllNamaMeja())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMeja.adapter = adapter
        nomorMeja = intent.getIntExtra("nomormeja", 0)
    }

    private fun setValuesToViews() {
        inputNamaPelangganEditText.setText(intent.getStringExtra("nama"))
    }
}
