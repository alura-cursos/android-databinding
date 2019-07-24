package br.com.alura.ceep.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.repository.NotaRepository
import br.com.alura.ceep.repository.Resource
import kotlinx.coroutines.Job

class FormularioNotaViewModel(private val repository: NotaRepository) : ViewModel() {

    private val job: Job = Job()

    fun buscaPorId(id: Long) = repository.buscaPorId(id)

    fun salva(nota: Nota): LiveData<Resource<Unit>> = repository.salva(nota, job)

}
