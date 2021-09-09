package br.com.rafaeldornelles.ui.listNotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.databinding.NotasFormFragmentBinding
import br.com.rafaeldornelles.model.db.NotasDatabase

class NotasFormFragment: Fragment() {
    lateinit var binding: NotasFormFragmentBinding
    lateinit var viewModel: NotasViewModel

    private val notasDao by lazy {
        NotasDatabase.getDatabase(NotasApplication.instance).notaDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = NotasFormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notasViewModelFactory = NotasViewModel.NotasViewModelFactory(notasDao)
        viewModel = ViewModelProvider(this, notasViewModelFactory).get(NotasViewModel::class.java)
    }
}