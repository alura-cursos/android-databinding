package br.com.alura.ceep.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.viewmodel.AppViewModel
import br.com.alura.ceep.ui.viewmodel.ComponentesVisuais
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val appViewModel: AppViewModel by viewModel()
    private val controlador by lazy {
        findNavController(R.id.activity_main_navhost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlador.addOnDestinationChangedListener { _, _, _ ->
            configuraComponentesVisuais()
        }
    }

    private fun configuraComponentesVisuais() {
        appViewModel.componentes.observe(this, Observer {
            it?.let { componentes ->
                configuraAppBar(componentes)
            }
        })
    }

    private fun configuraAppBar(componentes: ComponentesVisuais) {
        componentes.appBar?.let { appBarConfigurada ->
            supportActionBar?.show()
            title = appBarConfigurada.titulo
        } ?: supportActionBar?.hide()
    }

}