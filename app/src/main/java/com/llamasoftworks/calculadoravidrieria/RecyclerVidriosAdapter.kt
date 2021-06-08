package com.llamasoftworks.calculadoravidrieria

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.llamasoftworks.calculadoravidrieria.clases.Lamina

class RecyclerVidriosAdapter(
        private val listaVidrios: List<Lamina>,
        private var contexto: FragmentActivity
):RecyclerView.Adapter<RecyclerVidriosAdapter.MyViewHolder2>()  {
    inner class MyViewHolder2(view: View): RecyclerView.ViewHolder(view){
        val nombre: TextView
        val descripcion:EditText
        val btnDelete: Button
        val linearLayut: LinearLayout
        var bandera = false
        val btnEdit:Button
        init{
            nombre = view.findViewById(R.id.tv_nombre)
            descripcion = view.findViewById(R.id.et)
            btnDelete = view.findViewById(R.id.btn_delete)
            linearLayut = view.findViewById(R.id.linear)
            btnEdit = view.findViewById(R.id.btn_edit)
            nombre.setOnClickListener {
                bandera = !bandera
                if (bandera) {
                    linearLayut.visibility = View.VISIBLE
                } else {
                    linearLayut.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        //Definimos la interfaz a usar
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.lamina_recycler,
                parent,
                false
            )
        return MyViewHolder2(itemView)
    }

    override fun getItemCount(): Int {
        return  listaVidrios.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val carta = listaVidrios[position]
        holder.nombre.setText(carta.nombre)
        holder.descripcion.setText("""
            Medidas: ${carta.alto} x ${carta.ancho}
            Precio Lámina: ${carta.precioT}
            Precio Metro: ${carta.precioM}
        """.trimIndent())
        holder.linearLayut.visibility = View.GONE
        holder.btnDelete.setOnClickListener {
            VidriosFragment.list.removeAt(holder.layoutPosition)
            notifyDataSetChanged()
        }
        holder.btnEdit.setOnClickListener {
            contexto.startActivity(irNewVidrioFragment(holder))
        }
    }

    fun irNewVidrioFragment(holder:MyViewHolder2):Intent{
        Log.i("aaaap","YESSS")
        val intentExplicito = Intent(
                this.contexto,
                AddVidrio::class.java
        )
        intentExplicito.putExtra("lamina", holder.layoutPosition)
        return intentExplicito
    }
}