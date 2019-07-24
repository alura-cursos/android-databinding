package br.com.alura.ceep.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.ceep.model.Nota

@Dao
interface NotaDAO {

    @Query("SELECT * FROM Nota ORDER BY id DESC")
    fun buscaTodas(): LiveData<List<Nota>>

    @Insert(onConflict = REPLACE)
    fun salva(nota: Nota)

    @Query("SELECT * FROM Nota WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Nota?>

}