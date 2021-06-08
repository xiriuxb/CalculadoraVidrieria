package com.llamasoftworks.calculadoravidrieria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.llamasoftworks.calculadoravidrieria.clases.Lamina
import com.llamasoftworks.calculadoravidrieria.clases.Vidrio
import java.lang.NumberFormatException


class ListaFragment : Fragment() {

    companion object {
        var lista = mutableListOf<Vidrio>()

        fun calcularPrecio(vidrio:Vidrio):Double{
            return (((Math.ceil(vidrio.alto/10)*Math.ceil(vidrio.ancho/10))*vidrio.cantidad)/100)*vidrio.tipo.precioM
        }

        var pedazo = Vidrio(0.0,0.0, Lamina("",0.0,0.0,0.0,0.0),0)

        var listaSpinner = listOf<String>()

        var posicionSeleccionada = -1
    }

    override fun onStart() {
        super.onStart()
        this.activity?.let { iniciarRecyclerView(ListaFragment.lista, it) }
        val btnAdd = activity?.findViewById<Button>(R.id.btn_add_to_lista)
        if (btnAdd != null) {
            btnAdd.setOnClickListener {
                addPedazoToList()
                activity?.findViewById<RecyclerView>(R.id.rv_lista_vidrios)?.adapter?.notifyDataSetChanged()
            }
        }

        activity?.findViewById<EditText>(R.id.et_cantidad)
            ?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_NULL) {
                    addPedazoToList()
                    activity?.findViewById<RecyclerView>(R.id.rv_lista_vidrios)?.adapter?.notifyDataSetChanged()
                    return@OnEditorActionListener true
                }
                false
            })
        iniciarSpinner()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    fun iniciarRecyclerView(
        list: List<Vidrio>,
        activity: FragmentActivity
    ){
        val adaptadorUsuario = RecyclerListaAdapter(
            list, activity
        )
        val recycler = activity.findViewById<RecyclerView>(R.id.rv_lista_vidrios)
        adaptadorUsuario.notifyDataSetChanged()
        recycler.adapter = adaptadorUsuario
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun addPedazoToList(){
        val alto = activity?.findViewById<EditText>(R.id.etAlto2)
        val ancho = activity?.findViewById<EditText>(R.id.etAncho2)
        val cantidad = activity?.findViewById<EditText>(R.id.et_cantidad)
        val nTotalVid = activity?.findViewById<TextView>(R.id.nTotalVidrios)
        val nTotalMetros = activity?.findViewById<TextView>(R.id.nTotalMetros)
        val nTotalPrecio = activity?.findViewById<TextView>(R.id.tv_precio)
        val spinTipo = activity?.findViewById<Spinner>(R.id.spinner)
        var cant = cantidad?.text.toString()
        if (cant == "" || cant == "0") cant="1"
        try {
            if (posicionSeleccionada==-1){
                lista.add(
                        Vidrio(
                                alto?.text.toString().toDouble(),ancho?.text.toString().toDouble(),
                                VidriosFragment.list[spinTipo?.selectedItemPosition!!], cant.toInt()
                        )
                )
            }else{
                lista[posicionSeleccionada] = Vidrio(
                        alto?.text.toString().toDouble(),ancho?.text.toString().toDouble(),
                        VidriosFragment.list[spinTipo?.selectedItemPosition!!], cant.toInt()
                )
                posicionSeleccionada=-1
            }

        }catch (e: NumberFormatException){       }

        alto?.setText("")
        ancho?.setText("")
        cantidad?.setText("")
        alto?.requestFocus()
        nTotalVid?.text = calcularTotalVidrios().toString()
        nTotalMetros?.text = calcularTotalMetros().toString()
        nTotalPrecio?.text = String.format("%.2f", calcularPrecioTotal())
    }

    private fun calcularTotalVidrios():Int{
        return lista.fold(0){
            sum, element -> sum+element.cantidad
        }
    }

    private fun calcularTotalMetros():Double{
        var intp=0.0
        lista.forEach {
            intp += (Math.ceil(it.alto/10)*Math.ceil(it.ancho/10))*100* it.cantidad
        }
        return intp/10000
    }

    private fun iniciarSpinner(){
        val spinner: Spinner? = activity?.findViewById(R.id.spinner)
        listaSpinner = VidriosFragment.list.map { lamina -> lamina.nombre }
        spinner?.adapter = context?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item , listaSpinner) }
    }

    private fun calcularPrecioTotal():Double{
        var total = 0.0
        lista.forEach { total += calcularPrecio(it) }
        return total
    }
}