package br.com.alura.ceep.ui.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.mostraMensagem(mensagem: String, tempo: Int = Toast.LENGTH_SHORT){
    Toast.makeText(requireContext(), mensagem, tempo).show()
}