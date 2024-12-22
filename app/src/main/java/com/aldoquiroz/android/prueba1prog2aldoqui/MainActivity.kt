package com.aldoquiroz.android.prueba1prog2aldoqui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aldoquiroz.android.prueba1prog2aldoqui.logic.Orden
import com.aldoquiroz.android.prueba1prog2aldoqui.logic.Plato
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var orden: Orden
    private lateinit var tvPastelSubtotal: TextView
    private lateinit var tvCazuelaSubtotal: TextView
    private lateinit var tvTotalSinPropina: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotalConPropina: TextView
    private lateinit var etPastelQty: EditText
    private lateinit var etCazuelaQty: EditText
    private lateinit var swIncluirPropina: Switch
    private val pastelPrecio = 12000
    private val cazuelaPrecio = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orden = Orden()
        val pastel = Plato("Pastel de Choclo", pastelPrecio)
        val cazuela = Plato("Cazuela", cazuelaPrecio)

        tvPastelSubtotal = findViewById(R.id.tv_pastel_subtotal)
        tvCazuelaSubtotal = findViewById(R.id.tv_cazuela_subtotal)
        tvTotalSinPropina = findViewById(R.id.tv_total_without_tip)
        tvPropina = findViewById(R.id.tv_tip)
        tvTotalConPropina = findViewById(R.id.tv_total_with_tip)
        etPastelQty = findViewById(R.id.et_pastel_qty)
        etCazuelaQty = findViewById(R.id.et_cazuela_qty)
        swIncluirPropina = findViewById(R.id.sw_include_tip)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarSubtotales(pastel, cazuela)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        etPastelQty.addTextChangedListener(textWatcher)
        etCazuelaQty.addTextChangedListener(textWatcher)
        swIncluirPropina.setOnCheckedChangeListener { _, _ -> actualizarTotales() }
    }

    private fun actualizarSubtotales(pastel: Plato, cazuela: Plato) {
        val pastelCantidad = etPastelQty.text.toString().toIntOrNull() ?: 0
        val cazuelaCantidad = etCazuelaQty.text.toString().toIntOrNull() ?: 0

        orden = Orden()
        orden.agregarPlato(pastel, pastelCantidad)
        orden.agregarPlato(cazuela, cazuelaCantidad)

        tvPastelSubtotal.text = formatCurrency(pastelCantidad * pastelPrecio)
        tvCazuelaSubtotal.text = formatCurrency(cazuelaCantidad * cazuelaPrecio)
        actualizarTotales()
    }

    private fun actualizarTotales() {
        val incluirPropina = swIncluirPropina.isChecked
        val subtotal = orden.calcularSubtotal()
        val propina = orden.calcularPropina()
        val total = orden.calcularTotal(incluirPropina)

        tvTotalSinPropina.text = formatCurrency(subtotal)
        tvPropina.text = formatCurrency(if (incluirPropina) propina else 0)
        tvTotalConPropina.text = formatCurrency(total)
    }

    private fun formatCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return format.format(amount)
    }
}
