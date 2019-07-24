package br.com.alura.ceep.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.repository.NotaRepository

class ListaNotasViewModel(repository: NotaRepository) : ViewModel() {

    val todas: LiveData<List<Nota>> = repository.buscaTodas()

}