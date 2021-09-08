package br.com.rafaeldornelles.ui.ListNotas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rafaeldornelles.R
import br.com.rafaeldornelles.databinding.NotasFragmentBinding

class NotasFragment : Fragment() {
    private lateinit var viewModel: NotasViewModel
    private lateinit var binding: NotasFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotasFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
    }

}