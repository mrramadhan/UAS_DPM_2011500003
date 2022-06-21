package com.example.uasdpm2011500003

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntriDataDosenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_dosen)

        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golonganPangkat") &&
                intent.hasExtra("golonganPangkat") && intent.hasExtra("pendidikan") &&
                intent.hasExtra("keahlian") && intent.hasExtra("programStudi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolPangkat = findViewById<Spinner>(R.id.spnGolPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etBidKeahlian = findViewById<EditText>(R.id.etBidKeahlian)
        val etProdi = findViewById<EditText>(R.id.etProdi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val jabatan = arrayOf(
            "Tenaga Pengajar",
            "Asisten Ahli",
            "Lektor",
            "Lektor Kepala",
            "Guru Besar"
        )
        val adpJabatan = ArrayAdapter (
            this@EntriDataDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            jabatan
        )
        spnJabatan.adapter = adpJabatan

        val golPangkat = arrayOf(
            "III/a - Penata Muda",
            "III/b - Penata Muda Tingkat I",
            "III/c - Penata",
            "III/d - Penata Tingkat I",
            "IV/a - Pembina",
            "IV/b - Pembina Tingkat I",
            "IV/c - Pembina Utama Muda",
            "IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama"
        )

        val adpGolPangkat = ArrayAdapter (
            this@EntriDataDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            golPangkat
        )
        spnGolPangkat.adapter = adpGolPangkat

        if(modeEdit) {
            val nidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val Jabatan = intent.getStringExtra("jabatan")
            val golonganPangkat = intent.getStringExtra("golonganPangkat")
            val pendidikan = intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val programStudi = intent.getStringExtra("programStudi")

            etNIDN.setText(nidn)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(jabatan.indexOf(Jabatan))
            spnGolPangkat.setSelection(golPangkat.indexOf(golonganPangkat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidKeahlian.setText(keahlian)
            etProdi.setText(programStudi)
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                "${etBidKeahlian.text}".isNotEmpty() && "${etProdi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = DbHelper(this@EntriDataDosenActivity)
                db.nidn = "${etNIDN.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golPangkat = spnGolPangkat.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etBidKeahlian.text}"
                db.prodi = "${etProdi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")) {
                    Toast.makeText(
                        this@EntriDataDosenActivity,
                        "Data dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriDataDosenActivity,
                        "Data dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()

            } else
                Toast.makeText(
                    this@EntriDataDosenActivity,
                    "Data dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()

        }
    }
}