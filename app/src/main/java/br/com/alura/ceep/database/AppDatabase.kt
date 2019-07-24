package br.com.alura.ceep.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alura.ceep.database.dao.NotaDAO
import br.com.alura.ceep.model.Nota

@Database(version = 1, exportSchema = false, entities = [Nota::class])
abstract class AppDatabase : RoomDatabase() {

    abstract val notaDao: NotaDAO

}