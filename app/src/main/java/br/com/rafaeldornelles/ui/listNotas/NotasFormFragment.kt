package br.com.rafaeldornelles.ui.listNotas

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.databinding.NotasFormFragmentBinding
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.model.db.NotasDatabase
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModel
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.time.*
import java.time.LocalDateTime.ofInstant
import java.time.format.DateTimeFormatter
import java.util.*

class NotasFormFragment: Fragment() {
    lateinit var binding: NotasFormFragmentBinding

    lateinit var dateSelected: LocalDate
    lateinit var timeSelected: LocalTime
    lateinit var notaTitulo: String

    private val notasViewModel: NotasViewModel by viewModels {
        NotasViewModelFactory(NotasApplication.instance.repository)
    }

    private val notasDao by lazy {
        NotasDatabase.getDatabase(NotasApplication.instance).notaDao()
    }

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker().build()
    }

    private val timePicker by lazy {
        MaterialTimePicker.Builder().build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = NotasFormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textinputNotaData.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                if (!datePicker.isAdded) datePicker.show(parentFragmentManager, "datepicker")
            }
            datePicker.addOnPositiveButtonClickListener {
               dateSelected =
                   LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("UTC")).toLocalDate()
                setText(dateSelected.format(NotasApplication.dateFormatter))
            }
        }

        binding.textinputNotaHora.editText?.apply{
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener{
                if (!timePicker.isAdded) timePicker.show(parentFragmentManager, "timepicker")
            }
            timePicker.addOnPositiveButtonClickListener {
                timeSelected = LocalTime.of(timePicker.hour, timePicker.minute)
                setText(timeSelected.format(NotasApplication.timeFormatter))
            }
        }

        binding.btNotaCancelar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btNotaSalvar.setOnClickListener {
            salvarNota()
        }
    }

    fun salvarNota(){
        notaTitulo = binding.textinputNotaTitulo.editText?.text.toString()?:""
        if (!validaNota()) return
        val nota = Nota(0, notaTitulo, dateSelected, timeSelected)
        notasViewModel.insert(nota)
        findNavController().popBackStack()
    }

    fun validaNota(): Boolean {
        binding.textinputNotaTitulo.error = if (::notaTitulo.isInitialized && notaTitulo.isNotBlank())
            null else "Insira um título para a nota"
        binding.textinputNotaData.error = if (::dateSelected.isInitialized)
            null else "Selecione uma data"
        binding.textinputNotaHora.error = if (::timeSelected.isInitialized)
            null else "Selecione um horário"

        return listOf(binding.textinputNotaTitulo.error,
            binding.textinputNotaData.error,
            binding.textinputNotaTitulo).all { it == null }
    }
}