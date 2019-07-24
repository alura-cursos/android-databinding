package br.com.alura.ceep.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import br.com.alura.ceep.R
import kotlinx.android.synthetic.main.formulario_inserir_imagem.view.*

private const val INSERIR_URL = "Inserir URL"
private const val CANCELAR = "Cancelar"
private const val SALVAR = "Salvar"

class CarregaImagemDialog {

    fun mostra(
        context: Context,
        urlAtual: String = "",
        quandoTemUrlNova: (urlNova: String) -> Unit
    ) {
        LayoutInflater.from(context).inflate(
            R.layout.formulario_inserir_imagem,
            null,
            false
        )?.let { viewCriada ->
            configuraCampoUrl(viewCriada, urlAtual)?.let { campoUrl ->
                configuraDialog(context, viewCriada, campoUrl, quandoTemUrlNova)
            }
        }
    }

    private fun configuraDialog(
        context: Context,
        viewCriada: View,
        campoUrl: EditText,
        quandoTemUrlNova: (urlNova: String) -> Unit
    ): AlertDialog? {
        return AlertDialog.Builder(context)
            .setTitle(INSERIR_URL)
            .setView(viewCriada)
            .setPositiveButton(SALVAR) { _, _ ->
                val urlNova = campoUrl.text.toString()
                quandoTemUrlNova(urlNova)
            }
            .setNegativeButton(CANCELAR, null)
            .show()
    }

    private fun configuraCampoUrl(viewCriada: View, urlAtual: String): EditText? {
        val campoUrl = viewCriada.formulario_inserir_imagem_url ?: return null
        urlAtual.let(campoUrl::setText)
        return campoUrl
    }

}