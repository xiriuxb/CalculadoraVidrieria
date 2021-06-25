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
import kotlin.math.ceil
import kotlinx.android.synthetic.main.fragment_lista.*


class ListaFragment : Fragment() {

    companion object {
        var lista = mutableListOf<Vidrio>()

        fun calcularPrecio(vidrio:Vidrio):Double{
            return (((ceil(vidrio.alto/10)*ceil(vidrio.ancho/10))*vidrio.cantidad)/100)*vidrio.tipo.precioM
        }

        var pedazo = Vidrio(0.0,0.0, Lamina(""))

        var listaSpinner = listOf<String>()

        var posicionSeleccionada = -1
    }

    override fun onStart() {
        super.onStart()
        this.activity?.let { iniciarRecyclerView(lista, it) }
        btn_add_to_lista.setOnClickListener {
            addPedazoToList()
            rv_lista_vidrios.adapter?.notifyDataSetChanged()
        }
        //ES PARA dAR LA ACCION AL TECLADO CUANDO ESTA EN LA CASILLA DE CANTIDAD
        et_cantidad.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_NULL) {
                    addPedazoToList()
                    rv_lista_vidrios.adapter?.notifyDataSetChanged()
                    return@OnEditorActionListener true
                }
                false
            })
        iniciarSpinner()
    }

    override fun onResume() {
        super.onResume()
        calcTotales()
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
        var cant = et_cantidad.text.toString()
        if (cant == "" || cant == "0") cant="1"
        val vidrio = Vidrio(
            etAlto2.text.toString().toDouble(),etAncho2.text.toString().toDouble(),
            VidriosFragment.list[spinner.selectedItemPosition], cant.toInt()
        )
        try {
            if (posicionSeleccionada==-1){
                lista.add(vidrio)
            }else{
                lista[posicionSeleccionada] = vidrio
                posicionSeleccionada=-1
            }

        }catch (e: NumberFormatException){       }

        etAlto2.setText("")
        etAncho2.setText("")
        et_cantidad.setText("")
        etAlto2.requestFocus()
        calcTotales()
    }

    private fun calcularTotalVidrios():Int{
        return lista.fold(0){
            sum, element -> sum+element.cantidad
        }
    }

    private fun calcularTotalMetros():Double{
        var intp=0.0
        lista.forEach {
            intp += (ceil(it.alto/10)*ceil(it.ancho/10))*100* it.cantidad
        }
        return intp/10000
    }

    private fun iniciarSpinner(){
        listaSpinner = VidriosFragment.list.map { lamina -> lamina.nombre }
        spinner.adapter = context?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item , listaSpinner) }
    }

    private fun calcularPrecioTotal():Double{
        var total = 0.0
        lista.forEach { total += calcularPrecio(it) }
        return total
    }

    private fun calcTotales(){
        if (lista.size>0){
            nTotalVidrios.text = calcularTotalVidrios().toString()
            nTotalMetros.text = calcularTotalMetros().toString()
            tv_precio.text = String.format("%.2f", calcularPrecioTotal())
        }
    }
}