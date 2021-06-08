package com.llamasoftworks.calculadoravidrieria

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.llamasoftworks.calculadoravidrieria.clases.Lamina

class AddVidrio : AppCompatActivity() {

    var lamina:Lamina = Lamina("",0.0,0.0,0.0,0.0)
    var numeroEncontrado = -1

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vidrio)
        window.setLayout(1100,850)

    }

    override fun onStart() {
        super.onStart()
        numeroEncontrado = intent.getIntExtra("lamina", -1)
        if (numeroEncontrado!=-1){
            lamina = VidriosFragment.list[numeroEncontrado]
            loadData()
        }
        val btnGuardar = findViewById<Button>(R.id.button2)
        btnGuardar.setOnClickListener {
            saveNewVidrio()
            finish()
        }
    }

    fun saveNewVidrio(){
        val nombre = findViewById<TextInputEditText>(R.id.vidrio_nombre).text.toString()
        val alto = findViewById<TextInputEditText>(R.id.vidrio_alto_l).text.toString().toDouble()
        val ancho = findViewById<TextInputEditText>(R.id.vidrio_ancho_l).text.toString().toDouble()
        val precioTotal = findViewById<TextInputEditText>(R.id.vidrio_precio_t).text.toString().toDouble()
        val precioMetro = findViewById<TextInputEditText>(R.id.vidrio_precio_m).text.toString().toDouble()
        if (lamina.nombre==""){
            VidriosFragment.list.add(
                    Lamina( nombre,alto,ancho,precioTotal,precioMetro )
            )
        }else{
            lamina.nombre = nombre
            lamina.alto = alto
            lamina.ancho = ancho
            lamina.precioT = precioTotal
            lamina.precioM = precioMetro
            VidriosFragment.list[numeroEncontrado]=lamina
        }
    }

    private fun loadData(){
        findViewById<TextInputEditText>(R.id.vidrio_nombre).setText(lamina.nombre)
        findViewById<TextInputEditText>(R.id.vidrio_alto_l).setText(lamina.alto.toString())
        findViewById<TextInputEditText>(R.id.vidrio_ancho_l).setText(lamina.ancho.toString())
        findViewById<TextInputEditText>(R.id.vidrio_precio_t).setText(lamina.precioT.toString())
        findViewById<TextInputEditText>(R.id.vidrio_precio_m).setText(lamina.precioM.toString())
    }


}