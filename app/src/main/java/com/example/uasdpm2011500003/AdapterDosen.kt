package com.example.uasdpm2011500003

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterDosen (
    private val getContext: Context,
    private val customListItem: ArrayList<Dosen>
): ArrayAdapter<Dosen>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if(listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProdi = listLayout.findViewById(R.id.tvProdi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvNIDN!!.setText(listItem.nidn)
        holder.tvProdi!!.setText(listItem.prodi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, EntriDataDosenActivity::class.java)
            i.putExtra("nidn", listItem.nidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("jabatan", listItem.jabatan)
            i.putExtra("golonganPangkat", listItem.golPangkat)
            i.putExtra("pendidikan", listItem.pendidikan)
            i.putExtra("keahlian", listItem.keahlian)
            i.putExtra("programStudi", listItem.prodi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvNIDN!!.text
            val nama = holder.tvNmDosen!!.text
            val programStudi = holder.tvProdi!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda Yakin akan menghapus data ini??
                                   $nama
                                   [$nidn - $programStudi]
                """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }
        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvNIDN: TextView? = null
        internal var tvProdi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}