package com.aldoquiroz.android.prueba1prog2aldoqui.logic

class Orden {
    private val platos = mutableListOf<Pair<Plato, Int>>()

    fun agregarPlato(plato: Plato, cantidad: Int) {
        platos.add(Pair(plato, cantidad))
    }

    fun calcularSubtotal(): Int {
        return platos.sumBy { it.first.precio * it.second }
    }

    fun calcularPropina(): Int {
        return (calcularSubtotal() * 0.1).toInt()
    }

    fun calcularTotal(conPropina: Boolean): Int {
        val subtotal = calcularSubtotal()
        return if (conPropina) subtotal + calcularPropina() else subtotal
    }
}
