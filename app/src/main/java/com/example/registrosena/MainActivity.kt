package com.example.registrosena

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import modelo.Programa
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var txtNombre:EditText
    lateinit var txtApellido:EditText
    lateinit var txtIdentificacion:EditText
    lateinit var txtCorreo:EditText
    lateinit var cbProgramas:Spinner
    lateinit var btnInscripcion:Button
    lateinit var btnListaProgramas:Button
    lateinit var listaProgramas:MutableList<Programa>
    private var idPrograma:Int=0
    private var idUsuaro:Int=0
    private var urlBase:String="http://10.192.66.175:8080/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtIdentificacion = findViewById(R.id.txtIdentificacion)
        txtCorreo = findViewById(R.id.txtCorreo)
        btnInscripcion = findViewById(R.id.btnInscripcion)
        btnListaProgramas = findViewById(R.id.btnListaProgramas)
        cbProgramas = findViewById(R.id.cbProgramas)
        listaProgramas = mutableListOf<Programa>()

        btnInscripcion.setOnClickListener{agregar()}
        btnListaProgramas.setOnClickListener { listar() }

        obtenerProgramas()
    }

    private fun obtenerProgramas(){
        val url = urlBase + "programas/"
        val queue = Volley.newRequestQueue(this)
        val jsonProducts = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray>() { response ->
            try {
                val jsonArray = response
                listaProgramas.add(Programa(0, "Seleccione un programa"))
                for (i in 0 until jsonArray.length() ){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val nombre = jsonObject.getString("nombre")
                    val programa = Programa(id,nombre)
                    listaProgramas.add(programa)
                }
                val adaptador = ArrayAdapter<Programa>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    listaProgramas
                )
                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                cbProgramas.adapter=adaptador
            } catch (e:JSONException) {
                e.printStackTrace()
            }
        },
            Response.ErrorListener {
                error ->
                Toast.makeText( this,error.toString(), Toast.LENGTH_LONG).show()
            })
        queue.add(jsonProducts)
    }

    private fun agregar(){
        val url = urlBase + "usuarios/"
        val queue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(
            Request.Method.POST,url,Response.Listener { response ->
                Toast.makeText(this,"Registrado Exitosamente! ${response}", Toast.LENGTH_LONG).show()
                limpiar()
            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error al Agregar:${error.message}", Toast.LENGTH_LONG).show()
            }){
                override fun getParams(): MutableMap<String, String> {
                    val parametros = HashMap<String,String>()
                    parametros.put("nombre",txtNombre.text.toString())
                    parametros.put("apellido",txtApellido.text.toString())
                    parametros.put("identificacion",txtIdentificacion.text.toString())
                    parametros.put("correo",txtCorreo.text.toString())
                    var idPrograma = cbProgramas.selectedItemId ;
                    if (idPrograma <= 0){
                        Toast.makeText(this@MainActivity, "Seleccione un Programa", Toast.LENGTH_SHORT).show()
                        cbProgramas.selectedItemId
                    }else {
                        parametros.put("programas_registrados", idPrograma.toString())
                    }
                    println(parametros)
                    return parametros
            }
        }
        queue.add(resultadoPost)
    }

    private fun listar(){
        val intent = Intent(this,MainActivityInscritosPorPrograma::class.java)
        startActivity(intent)
    }

    private fun limpiar(){
        txtNombre.setText("")
        txtApellido.setText("")
        txtCorreo.setText("")
        txtIdentificacion.setText("")
        cbProgramas.setSelection(0)
    }

}