package br.com.rafaeldornelles.ui.listNotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rafaeldornelles.model.db.NotaDao

class NotasViewModel(val dao: NotaDao) : ViewModel() {

    class NotasViewModelFactory(private val dao: NotaDao): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(NotasViewModel::class.java))
                NotasViewModel(dao) as T else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}