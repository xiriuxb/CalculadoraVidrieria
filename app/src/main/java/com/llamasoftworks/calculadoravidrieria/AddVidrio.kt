package com.llamasoftworks.calculadoravidrieria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.llamasoftworks.calculadoravidrieria.clases.Lamina
import kotlinx.android.synthetic.main.activity_add_vidrio.*

class AddVidrio : AppCompatActivity() {

    var lamina:Lamina = Lamina("")
    var numeroEncontrado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vidrio)
        window.setLayout(1100,930)
    }

    override fun onStart() {
        super.onStart()
        numeroEncontrado = intent.getIntExtra("lamina", -1)
        if (numeroEncontrado!=-1){
            lamina = VidriosFragment.list[numeroEncontrado]
            loadData()
        }
        button2.setOnClickListener {
            saveNewVidrio()
            finish()
        }
    }

    fun saveNewVidrio(){
        val nombre = vidrio_nombre.text.toString()
        val alto = vidrio_alto_l.text.toString().toDouble()
        val ancho = vidrio_ancho_l.text.toString().toDouble()
        val precioTotal = vidrio_precio_t.text.toString().toDouble()
        val precioMetro = vidrio_precio_m.text.toString().toDouble()
        val precioMetro2 = vidrio_precio_m_2.text.toString().toDouble()
        when (lamina.nombre){
            ""->VidriosFragment.list.add(Lamina(nombre,alto,ancho,precioTotal,precioMetro,precioMetro2))
            else -> VidriosFragment.list[numeroEncontrado]= Lamina(nombre, alto, ancho, precioTotal, precioMetro,precioMetro2)
        }
    }

    private fun loadData(){
        vidrio_nombre.setText(lamina.nombre)
        vidrio_alto_l.setText(lamina.alto.toString())
        vidrio_ancho_l.setText(lamina.ancho.toString())
        vidrio_precio_t.setText(lamina.precioT.toString())
        vidrio_precio_m.setText(lamina.precioM.toString())
        vidrio_precio_m_2.setText(lamina.precioDos.toString())
    }
}