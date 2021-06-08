package com.llamasoftworks.calculadoravidrieria

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.llamasoftworks.calculadoravidrieria.clases.Lamina
import com.llamasoftworks.calculadoravidrieria.ui.main.SectionsPagerAdapter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        leerCrearArchivo()
    }

    fun leerCrearArchivo(){
        val file = File(filesDir, "vidrios.txt")
        if (file.exists()){
            val contenido = applicationContext.openFileInput("vidrios.txt").bufferedReader().readLines()[0]
            if (contenido[1].toString().equals("{")){
                val obj = Json.decodeFromString(ListSerializer(Lamina.serializer()),contenido)
                VidriosFragment.list = obj.toMutableList()
            }
        }else{
            applicationContext.openFileOutput("vidrios.txt", Context.MODE_PRIVATE).use {
                it.write("".toByteArray())
            }
        }
    }
}