package modelo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.registrosena.R
import modelo.Inscritos

class adaptador: BaseAdapter {
    lateinit var contexto:Context
    var layout:Int=0
    lateinit var listaInscritos:List<Inscritos>

    constructor(context: Context,layout:Int,listaInscritos: List<Inscritos>){
        this.contexto = context
        this.layout = layout
        this.listaInscritos = listaInscritos
    }

    override fun getCount(): Int {
        return listaInscritos.size
    }

    override fun getItem(position: Int): Any {
        return listaInscritos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, vista: View?, parent: ViewGroup?): View {
        var v:View
        var inflater:LayoutInflater = LayoutInflater.from(contexto)
        v = inflater.inflate(R.layout.layoutinscritos,null)
        val txtNombre:TextView = v.findViewById(R.id.txtPrograma)
        txtNombre.text = listaInscritos[position].nombre
        val txtCantidad : TextView = v.findViewById(R.id.txtCantidad)
        txtCantidad.text = listaInscritos[position].cantidad.toString()
        return v;
    }
}