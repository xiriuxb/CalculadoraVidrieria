package com.llamasoftworks.calculadoravidrieria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.llamasoftworks.calculadoravidrieria.clases.Lamina
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class VidriosFragment : Fragment() {
    override fun onStart() {
        super.onStart()
        this.activity?.let { iniciarRecyclerView(list, it) }
    }

    override fun onResume() {
        super.onResume()
        this.activity?.let { iniciarRecyclerView(list, it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vidrios, container, false)
        val btn: FloatingActionButton? = root.findViewById(R.id.fab)
        if (btn != null) {
            btn.setOnClickListener {
                irCartaActivity()
            }
        }
        val btnSaveAll: FloatingActionButton? = root.findViewById(R.id.fab_save_all)
        if (btnSaveAll != null) {
            btnSaveAll.setOnClickListener{
                saveAll()
            }
        }
        return root
    }

    companion object {
        var list:MutableList<Lamina> = mutableListOf()
    }

    private fun irCartaActivity(){
        val intentExplicito = Intent(
            activity,
            AddVidrio::class.java
        )
        startActivity(intentExplicito)
    }

    fun iniciarRecyclerView(
        list: MutableList<Lamina>,
        activity: FragmentActivity
    ){
        val adaptadorUsuario = RecyclerVidriosAdapter(
            list,activity
        )
        val recycler = activity.findViewById<RecyclerView>(R.id.rv_lista_tipos_vidrios)
        adaptadorUsuario.notifyDataSetChanged()
        recycler.adapter = adaptadorUsuario
        recycler.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(activity)
    }

    fun saveAll(){
        val content = Json.encodeToString(ListSerializer(Lamina.serializer()), list)
        val filename = "vidrios.txt"
        context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
            if (it != null) {
                it.write(content.toByteArray())
            }
        }
    }
}