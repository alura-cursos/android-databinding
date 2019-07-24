package br.com.alura.ceep.ui.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.ceep.R
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.repository.Falha
import br.com.alura.ceep.repository.Sucesso
import br.com.alura.ceep.ui.dialog.CarregaImagemDialog
import br.com.alura.ceep.ui.extensions.carregaImagem
import br.com.alura.ceep.ui.viewmodel.AppBar
import br.com.alura.ceep.ui.viewmodel.AppViewModel
import br.com.alura.ceep.ui.viewmodel.ComponentesVisuais
import br.com.alura.ceep.ui.viewmodel.FormularioNotaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.formulario_nota.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FormularioNotaFragment : Fragment() {

    private val argumentos by navArgs<FormularioNotaFragmentArgs>()
    private val appViewModel: AppViewModel by sharedViewModel()
    private val notaId by lazy {
        argumentos.notaId
    }
    private val viewModel: FormularioNotaViewModel by viewModel()
    private val controlador by lazy {
        findNavController()
    }
    private lateinit var notaEncontrada: Nota
    private var urlAtual: String = ""
    private val campoTitulo: EditText by lazy {
        formulario_nota_titulo
    }
    private val campoDescricao: EditText by lazy {
        formulario_nota_descricao
    }
    private val campoFavorita: CheckBox by lazy {
        formulario_nota_favorita
    }
    private val campoImagem: ImageView by lazy {
        formulario_nota_imagem
    }
    private val fabAdicionaImagem: FloatingActionButton by lazy {
        formulario_nota_fab_adiciona_imagem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.formulario_nota,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tentaBuscarNota()
        configuraFab()
        configuraCampoImagem()
    }

    private fun tentaBuscarNota() {
        appViewModel.temComponentes = appBarParaCriacao()
        if (temIdValido()) {
            viewModel.buscaPorId(notaId).observe(this, Observer {
                it?.let { notaEncontrada ->
                    inicializaNota(notaEncontrada)
                    inicializaCampos()
                    appViewModel.temComponentes = appBarParaEdicao()
                }
            })
        } else {
            inicializaNota(Nota())
        }
    }

    private fun temIdValido() = notaId != 0L

    private fun configuraFab() {
        fabAdicionaImagem.setOnClickListener {
            solicitaImagem()
        }
    }

    private fun configuraCampoImagem() {
        configuraVisibilidadeDeComponentes()
        campoImagem.setOnClickListener {
            solicitaImagem()
        }
    }

    private fun solicitaImagem() {
        CarregaImagemDialog().mostra(requireContext(), urlAtual) { urlNova ->
            this.urlAtual = urlNova
            configuraImagem()
        }
    }

    private fun configuraVisibilidadeDeComponentes() {
        if (urlAtual.isEmpty()) {
            campoImagem.visibility = GONE
            fabAdicionaImagem.show()
        } else {
            campoImagem.visibility = VISIBLE
            fabAdicionaImagem.hide()
        }
    }

    private fun inicializaNota(notaEncontrada: Nota) {
        this.notaEncontrada = notaEncontrada
        urlAtual = this.notaEncontrada.imagemUrl
    }

    private fun inicializaCampos() {
        if (::notaEncontrada.isInitialized) {
            campoTitulo.setText(notaEncontrada.titulo)
            campoDescricao.setText(notaEncontrada.descricao)
            campoFavorita.isChecked = notaEncontrada.favorita
            configuraImagem()
        }
    }

    private fun configuraImagem() {
        configuraVisibilidadeDeComponentes()
        campoImagem.carregaImagem(urlAtual)
    }

    private fun appBarParaEdicao() = ComponentesVisuais(appBar = AppBar(titulo = "Editando nota"))

    private fun appBarParaCriacao() = ComponentesVisuais(appBar = AppBar(titulo = "Criando nota"))

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.formulario_nota_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.formulario_nota_menu_salva) {
            val notaCriada = criaNota()
            salva(notaCriada)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun criaNota(): Nota {
        val titulo = formulario_nota_titulo.text.toString()
        val descricao = formulario_nota_descricao.text.toString()
        val favorita = formulario_nota_favorita.isChecked
        return notaEncontrada.copy(
            titulo = titulo,
            descricao = descricao,
            favorita = favorita,
            imagemUrl = urlAtual
        )
    }

    private fun salva(notaNova: Nota) {
        viewModel.salva(notaNova).observe(this, Observer { resource ->
            when (resource) {
                is Sucesso -> controlador.popBackStack()
                is Falha -> resource.erro?.run { mostraMensagem(this) }
            }
        })
    }

}

