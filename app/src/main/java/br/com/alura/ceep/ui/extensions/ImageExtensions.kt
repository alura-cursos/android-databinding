package br.com.alura.ceep.ui.extensions

import android.widget.ImageView
import br.com.alura.ceep.R
import com.bumptech.glide.Glide

fun ImageView.carregaImagem(
    endereco: String,
    imagemDeErro: Int = R.drawable.erro_carrega_imagem
) {
    Glide.with(this)
        .load(endereco)
        .error(imagemDeErro)
        .centerCrop()
        .into(this)
}