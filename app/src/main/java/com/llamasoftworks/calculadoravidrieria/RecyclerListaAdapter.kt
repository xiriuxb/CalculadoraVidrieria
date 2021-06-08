package com.llamasoftworks.calculadoravidrieria

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.llamasoftworks.calculadoravidrieria.clases.Vidrio

class RecyclerListaAdapter(
    private val listaVidrios: List<Vidrio>,
    private val contexto: FragmentActivity
):RecyclerView.Adapter<RecyclerListaAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Definimos la interfaz a usar
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.lamina_recycler,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  listaVidrios.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pedazo = listaVidrios[position]
        holder.nombre.setText("""(${pedazo.tipo.nombre}) ${pedazo.alto} x ${pedazo.ancho} = ${pedazo.cantidad}""".trimIndent())
        holder.descripcion.setText("""${ListaFragment.calcularPrecio(pedazo)}""")
        holder.linearLayut.visibility = View.GONE
        holder.btnDelete.setOnClickListener {
            ListaFragment.lista.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnEdit.setOnClickListener {
            loadToEdit(position)
        }
    }

    fun loadToEdit(posicion:Int){
        ListaFragment.posicionSeleccionada = posicion
        val pedazo = ListaFragment.lista[posicion]
        contexto.findViewById<EditText>(R.id.etAlto2).setText(pedazo.alto.toString())
        contexto.findViewById<EditText>(R.id.etAncho2).setText(pedazo.ancho.toString())
        contexto.findViewById<EditText>(R.id.et_cantidad).setText(pedazo.cantidad.toString())
        contexto.findViewById<Spinner>(R.id.spinner).setSelection(ListaFragment.listaSpinner.indexOf(pedazo.tipo.nombre))
    }
}