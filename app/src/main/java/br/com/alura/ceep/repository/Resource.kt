package br.com.alura.ceep.repository

abstract class Resource<T>(val dado: T? = null, val erro: String? = null)

class Sucesso<T>(dado: T? = null) : Resource<T>(dado = dado)

class Falha<T>(erro: String) : Resource<T>(erro = erro)