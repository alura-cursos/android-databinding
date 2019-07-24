package br.com.alura.ceep.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private val _componentes = MutableLiveData<ComponentesVisuais>().also {
        it.value = temComponentes
    }

    val componentes: LiveData<ComponentesVisuais> = _componentes

    var temComponentes = ComponentesVisuais()
        set(value) {
            field = value
            _componentes.value = value
        }

}

class ComponentesVisuais(val appBar: AppBar? = null)

class AppBar(val titulo: String = "")
