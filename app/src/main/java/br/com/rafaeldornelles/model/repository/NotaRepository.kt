package br.com.rafaeldornelles.model.repository

import androidx.annotation.WorkerThread
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.model.db.NotaDao
import kotlinx.coroutines.flow.Flow

class NotaRepository(private val notaDao: NotaDao) {
    val allNotas: Flow<List<Nota>> = notaDao.all()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(nota: Nota){
        notaDao.insert(nota)
    }
}