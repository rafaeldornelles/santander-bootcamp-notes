package br.com.rafaeldornelles.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.rafaeldornelles.model.Nota

@Dao
interface NotaDao {
    @Query("SELECT * FROM Nota")
    fun all() : LiveData<List<Nota>>

    @Query("SELECT * FROM Nota WHERE id = :id")
    fun findById(id:Int) : LiveData<Nota>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nota: Nota)

    @Delete
    fun delete(nota: Nota)
}