package br.com.rafaeldornelles

import android.app.Application
import br.com.rafaeldornelles.model.repository.NotaRepository
import br.com.rafaeldornelles.model.db.NotasDatabase

class NotasApplication: Application() {
    val database by lazy{ NotasDatabase.getDatabase(this) }
    val repository by lazy { NotaRepository(database.notaDao()) }

    override fun onCreate() {
        super.onCreate()
        NotasApplication.instance = this
    }

    companion object{
        lateinit var instance: NotasApplication
    }
}