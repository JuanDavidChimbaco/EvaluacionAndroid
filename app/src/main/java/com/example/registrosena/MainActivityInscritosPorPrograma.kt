package com.example.registrosena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import modelo.Inscritos
import modelo.adaptador
import org.json.JSONException

class MainActivityInscritosPorPrograma : AppCompatActivity() {

    private lateinit var listaIncritos:MutableList<Inscritos>
    private lateinit var listViewIncritos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_inscritos_por_programa)

        listaIncritos = mutableListOf()
        listViewIncritos = findViewById(R.id.listaInscritos)
        obtenerInscritos()

    }
    private fun obtenerInscritos(){
        val url = "http://10.192.66.175:8080/api/inscritos-por-programa/"
        val queue = Volley.newRequestQueue(this)
        val jsonInscritos = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()){
                        val jsonObject = response.getJSONObject(i)
                        val nombre =    jsonObject.getString("programa_nombre")
                        val cantidad = jsonObject.getString("cantidad_inscritos")
                        val inscritos = Inscritos(nombre,cantidad)
                        listaIncritos.add(inscritos)
                    }
                    val adaptador = adaptador(this,R.layout.layoutinscritos,listaIncritos)
                    listViewIncritos.adapter = adaptador
                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },{ error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        })
        queue.add(jsonInscritos)
    }
}