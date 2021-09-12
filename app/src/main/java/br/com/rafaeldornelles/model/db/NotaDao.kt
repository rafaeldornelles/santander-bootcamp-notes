package br.com.rafaeldornelles.model.db

import androidx.room.*
import br.com.rafaeldornelles.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM Nota")
    fun all() : Flow<List<Nota>>

    @Query("SELECT * FROM Nota WHERE id = :id")
    fun findById(id:Int) : Flow<Nota>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(nota: Nota): Long

    @Delete
    fun delete(nota: Nota)
}