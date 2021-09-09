package br.com.rafaeldornelles.ui.listNotas.viewmodel

import androidx.lifecycle.*
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.model.repository.NotaRepository
import kotlinx.coroutines.launch

class NotasViewModel(private val repository: NotaRepository) : ViewModel() {
    val notas: LiveData<List<Nota>> get() = repository.allNotas.asLiveData()

    fun insert(nota: Nota) = viewModelScope.launch {
        repository.insert(nota)
    }
}

class NotasViewModelFactory(private val repository: NotaRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NotasViewModel::class.java))
            NotasViewModel(repository) as T else throw IllegalArgumentException("Unknown ViewModel class")
    }
}