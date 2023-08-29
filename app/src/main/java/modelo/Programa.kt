package modelo

class Programa constructor(id:Int, nombre:String /*ficha:Int, codigo:Int, cupo:Int, duracionMeses:Int, jornada:String*/) {
    var id = id
    var nombre = nombre
    /*var ficha = ficha
    var codigo = codigo
    var cupo = cupo
    var duracionMeses = duracionMeses
    var jornada = jornada*/

    override fun toString(): String {
        return nombre
    }
}