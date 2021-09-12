package br.com.rafaeldornelles.ui.listNotas.viewmodel

import androidx.lifecycle.*
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.model.repository.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotasViewModel(private val repository: NotaRepository) : ViewModel() {
    val notas: LiveData<List<Nota>> get() = repository.allNotas.asLiveData()

    fun save(nota: Nota): LiveData<Long?> {
        val liveData = MutableLiveData<Long?>()
        viewModelScope.launch {
            liveData.value = withContext(Dispatchers.IO) {
                repository.save(nota)
            }
        }
        return liveData
    }

    fun delete(nota: Nota){ viewModelScope.launch {
        withContext(Dispatchers.IO){ repository.delete(nota) }
        }
    }
}

class NotasViewModelFactory(private val repository: NotaRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NotasViewModel::class.java))
            NotasViewModel(repository) as T else throw IllegalArgumentException("Unknown ViewModel class")
    }
}