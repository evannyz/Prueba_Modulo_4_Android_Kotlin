package com.evannyz.pruebamodulocuatroapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote.ProductosRepository
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote.ProductosRetrofitClient
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.logic.Descuento
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.logic.ValidadorSegunEstado
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation.ProductosViewModel
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation.ProductosViewModelFactory
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation.ProductosViewState
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.ui.adapter.ListaDeProductosAdapter
import com.evannyz.pruebamodulocuatroapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var rawBindind: ActivityMainBinding? = null
    private val binding get() = rawBindind!!
    private lateinit var productosViewModel: ProductosViewModel
    private var total = 0
    private var validador: ValidadorSegunEstado = ValidadorSegunEstado()
    private var descuento: Descuento = Descuento()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rawBindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDependencies()
        initAllTextViews()
        setupViewModel()
        setupRecyclerView()
    }

    private fun initAllTextViews() {
        binding.tvSubtotal.text = "Sub Total: $0"
        binding.tvTotal.text = "Total: $0.0"
    }

    private fun initDependencies() {
        val productosApi = ProductosRetrofitClient.crearPreLoginApi()
        val productosDataRepository = ProductosRepository(productosApi)
        val producrosViewModelFactory = ProductosViewModelFactory(productosDataRepository)
        productosViewModel =
            ViewModelProvider(this, producrosViewModelFactory)[ProductosViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.rvProductos.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun setupViewModel() {
        productosViewModel.state().observe(this) {
            it?.let { safeState ->
                handleUI(safeState)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        productosViewModel.obtenerProductos()
    }


    private fun handleUI(safeState: ProductosViewState) {
        when (safeState) {
            is ProductosViewState.MostrarListaVaciaViewState -> showViewWithoutProducts()
            is ProductosViewState.MostrarProductosViewState -> showProductos(safeState)
            is ProductosViewState.ServerErrorViewState -> goToErrorView()
            is ProductosViewState.NoHayInternetViewState -> goToNoInternetView()
        }
    }

    private fun goToNoInternetView() {
        alert("No hay internet")
    }

    private fun goToErrorView() {
        alert("Error de servidor")
    }

    fun alert(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showViewWithoutProducts() {
        alert("No hay productos que mostrar")
    }

    private fun showProductos(safeState: ProductosViewState.MostrarProductosViewState) {
        var adapter = ListaDeProductosAdapter(safeState.productos)
        adapter.setListener { view, position, precio, estado ->
            total = validador.compruebaEstadoDevuelveTotal(estado, precio, total)
            var totalFinal: Double = descuento.aplicarDescuentoAlTotal(total)
            binding.tvSubtotal.text = "Sub Total : $$total"
            binding.tvTotal.text = "Total: $$totalFinal"
        }
        binding.rvProductos.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        rawBindind = null
    }
}