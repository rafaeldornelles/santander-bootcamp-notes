package br.com.rafaeldornelles.ui.listNotas.viewmodel

import android.util.Log
import androidx.lifecycle.*
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.model.repository.NotaRepository
import br.com.rafaeldornelles.ui.listNotas.NotasListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NotasViewModel(private val repository: NotaRepository) : ViewModel() {
    val notas: LiveData<List<Nota>> get() = repository.allNotas.asLiveData()

    fun save(nota: Nota, listener: NotasListener) = viewModelScope.launch {
        try {
            val notaId = withContext(Dispatchers.IO){
                repository.save(nota)
            }
            if (notaId < 0) throw Exception()
            listener.onInsertSuccess()
        } catch (e: Exception){
            Log.d("DEBUG", e.message.toString())
            listener.onInsertError()
        }
    }

    fun delete(nota: Nota){ viewModelScope.launch {
        withContext(Dispatchers.IO){ repository.delete(nota) }
        }
    }
}

class NotasViewModelFactory(private val repository: NotaRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NotasViewModel::class.java))
            NotasViewModel(repository) as T else throw IllegalArgumentException("Unknown ViewModel class")
    }
}