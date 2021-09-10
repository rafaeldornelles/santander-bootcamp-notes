package br.com.rafaeldornelles

import android.app.Application
import br.com.rafaeldornelles.model.repository.NotaRepository
import br.com.rafaeldornelles.model.db.NotasDatabase
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NotasApplication: Application() {
    val database by lazy{ NotasDatabase.getDatabase(this) }
    val repository by lazy { NotaRepository(database.notaDao()) }

    override fun onCreate() {
        super.onCreate()
        NotasApplication.instance = this
    }

    companion object{
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        lateinit var instance: NotasApplication
    }
}