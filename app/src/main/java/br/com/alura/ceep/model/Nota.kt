package br.com.alura.ceep.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var titulo: String = "",
    var descricao: String = "",
    var favorita: Boolean = false,
    var imagemUrl: String = ""
)