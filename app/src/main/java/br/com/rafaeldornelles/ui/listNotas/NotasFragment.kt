package br.com.rafaeldornelles.ui.listNotas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.R
import br.com.rafaeldornelles.databinding.NotasFragmentBinding
import br.com.rafaeldornelles.model.db.NotasDatabase

class NotasFragment : Fragment() {
    private lateinit var viewModel: NotasViewModel
    private lateinit var binding: NotasFragmentBinding

    private val notasDao by lazy {
        NotasDatabase.getDatabase(NotasApplication.instance).notaDao()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotasFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notasViewModelFactory = NotasViewModel.NotasViewModelFactory(notasDao)
        viewModel = ViewModelProvider(this, notasViewModelFactory).get(NotasViewModel::class.java)

        binding.notasButtonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_notasFragment_to_notasFormFragment)
        }
    }


}