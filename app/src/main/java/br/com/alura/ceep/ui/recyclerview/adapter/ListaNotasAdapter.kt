package br.com.alura.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.extensions.carregaImagem
import kotlinx.android.synthetic.main.item_nota.view.*

class ListaNotasAdapter(
    private val context: Context,
    var onItemClickListener: (notaSelecionada: Nota) -> Unit = {}
) : ListAdapter<Nota, ListaNotasAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nota ->
            holder.vincula(nota)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var nota: Nota
        private val campoTitulo: TextView by lazy {
            itemView.item_nota_titulo
        }
        private val campoDescricao: TextView by lazy {
            itemView.item_nota_descricao
        }
        private val campoFavorita: ImageView by lazy {
            itemView.item_nota_favorita
        }
        private val campoImagem: ImageView by lazy {
            itemView.item_nota_imagem
        }

        init {
            itemView.setOnClickListener {
                if (::nota.isInitialized) {
                    onItemClickListener(nota)
                }
            }
        }

        fun vincula(nota: Nota) {
            this.nota = nota
            campoTitulo.text = nota.titulo
            campoDescricao.text = nota.descricao
            if (this.nota.favorita) {
                campoFavorita.visibility = VISIBLE
            } else {
                campoFavorita.visibility = GONE
            }
            campoImagem.carregaImagem(nota.imagemUrl)
            if (nota.imagemUrl.isEmpty()) {
                campoImagem.visibility = GONE
            } else {
                campoImagem.visibility = VISIBLE
            }
        }

    }

}

object DiffCallback : DiffUtil.ItemCallback<Nota>() {
    override fun areItemsTheSame(
        oldItem: Nota,
        newItem: Nota
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Nota, newItem: Nota) = oldItem == newItem

}