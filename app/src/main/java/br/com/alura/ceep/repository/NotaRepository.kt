package br.com.alura.ceep.repository

import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.database.dao.NotaDAO
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val FALHA_AO_SALVAR_NOTA = "Falha ao salvar nota"

class NotaRepository(private val dao: NotaDAO) {

    fun salva(nota: Nota, job: Job = Job()) =
        MutableLiveData<Resource<Unit>>().also { liveData ->
            CoroutineScope(IO + job).launch {
                val resource: Resource<Unit> = try {
                    dao.salva(nota)
                    Sucesso()
                } catch (e: Exception) {
                    Falha(FALHA_AO_SALVAR_NOTA)
                }
                liveData.postValue(resource)
            }
        }

    fun buscaTodas() = dao.buscaTodas()

    fun buscaPorId(id: Long) = dao.buscaPorId(id)

}