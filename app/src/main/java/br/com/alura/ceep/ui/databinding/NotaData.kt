package br.com.alura.ceep.ui.databinding

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import br.com.alura.ceep.model.Nota

class NotaData(
    private var nota: Nota = Nota(),
    val titulo: ObservableField<String> = ObservableField(nota.titulo),
    val descricao: ObservableField<String> = ObservableField(nota.descricao),
    val favorita: ObservableBoolean = ObservableBoolean(nota.favorita),
    val imagemUrl: ObservableField<String> = ObservableField(nota.imagemUrl)
    ) {

    fun atualiza(nota: Nota) {
        this.nota = nota
        titulo.set(this.nota.titulo)
        descricao.set(this.nota.descricao)
        favorita.set(this.nota.favorita)
        imagemUrl.set(this.nota.imagemUrl)
    }

    fun paraNota(): Nota? {
        return this.nota.copy(
            titulo = titulo.get() ?: return null,
            descricao = descricao.get() ?: return null,
            favorita = favorita.get(),
            imagemUrl = imagemUrl.get() ?: return null
        )
    }
}