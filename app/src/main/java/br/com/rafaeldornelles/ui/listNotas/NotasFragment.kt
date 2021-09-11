package br.com.rafaeldornelles.ui.listNotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.R
import br.com.rafaeldornelles.databinding.FragmentNotasBinding
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModel
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModelFactory

class NotasFragment : Fragment() {
    private val notasViewModel: NotasViewModel by viewModels {
        NotasViewModelFactory(NotasApplication.instance.repository)
    }

    private lateinit var binding: FragmentNotasBinding

    private val notaAdapter by lazy { NotasAdapter(notas) }
    private val notas = mutableListOf<Nota>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notasButtonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_notasFragment_to_notasFormFragment)
        }
        
        binding.notasRecyclerView.adapter = notaAdapter

        notasViewModel.notas.observeForever{
            notas.clear()
            notas.addAll(it)
            notaAdapter.notifyDataSetChanged()
        }
    }


}