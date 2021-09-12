package br.com.rafaeldornelles.ui.listNotas

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.databinding.FragmentNotasFormBinding
import br.com.rafaeldornelles.model.Nota
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModel
import br.com.rafaeldornelles.ui.listNotas.viewmodel.NotasViewModelFactory
import br.com.rafaeldornelles.util.DateTimeUtils
import br.com.rafaeldornelles.util.NotasAppExtensions.setText
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.time.LocalDate
import java.time.LocalTime

class NotasFormFragment: Fragment() {
    private lateinit var binding: FragmentNotasFormBinding
    private val datePickerTag = "datepicker"
    private val timePickerTag = "timepicker"

    private val notasViewModel: NotasViewModel by viewModels { NotasViewModelFactory(NotasApplication.instance.repository) }

    private val datePicker by lazy { MaterialDatePicker.Builder.datePicker().build() }
    private val timePicker by lazy { MaterialTimePicker.Builder().build() }

    private lateinit var notaTitulo: String
    private lateinit var dateSelected: LocalDate
    private lateinit var timeSelected: LocalTime

    private val args: NotasFormFragmentArgs by navArgs()
    private val nota by lazy { args.nota }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNotasFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializarCampos()
        configurarDatePicker()
        configurarTimePicker()
        configurarBotaoCancelar()
        configurarBotaoSalvar()
    }

    private fun inicializarCampos(){
        nota?.apply {
            binding.textinputNotaTitulo.setText(this.titulo)
            dateSelected = this.data
            binding.textinputNotaData.setText(dateSelected.format(NotasApplication.dateFormatter))
            timeSelected = this.horario
            binding.textinputNotaHora.setText(timeSelected.format(NotasApplication.timeFormatter))
        }
    }

    private fun configurarBotaoSalvar() {
        binding.btNotaSalvar.setOnClickListener {
            salvarNota()
        }
    }

    private fun configurarBotaoCancelar() {
        binding.btNotaCancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun configurarDatePicker() {
        binding.textinputNotaData.editText?.apply {
            keyListener = null
            setOnClickListener {
                showDatePicker()
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) showDatePicker()
            }
        }

        datePicker.addOnPositiveButtonClickListener {
            dateSelected = DateTimeUtils.localDateFromEpochMIlli(it)
            binding.textinputNotaData.setText(dateSelected.format(NotasApplication.dateFormatter))
        }
    }

    private fun configurarTimePicker() {
        binding.textinputNotaHora.editText?.apply {
            keyListener = null
            setOnClickListener {
                showTimePicker()
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) showTimePicker()
            }
        }

        timePicker.addOnPositiveButtonClickListener {
            timeSelected = LocalTime.of(timePicker.hour, timePicker.minute)
            binding.textinputNotaHora.setText(timeSelected.format(NotasApplication.timeFormatter))
        }
    }

    private fun showDatePicker(){
        if (!datePicker.isAdded) datePicker.show(parentFragmentManager, datePickerTag)
    }

    private fun showTimePicker(){
        if (!timePicker.isAdded) timePicker.show(parentFragmentManager, timePickerTag)
    }

    private fun salvarNota(){
        notaTitulo = binding.textinputNotaTitulo.editText?.text?.toString()?:""
        if (!validaNota()) return
        val notaId = nota?.id ?: 0
        val notaEdit = Nota(notaId, notaTitulo, dateSelected, timeSelected)
        notasViewModel.save(notaEdit).observe(viewLifecycleOwner){
            if (it != null && it > 0) findNavController().popBackStack()
            else AlertDialog.Builder(context)
                .setTitle("Erro ao salvar nota!")
                .setMessage("Não foi possível salvar a nota. Tente novamente mais tarde.")
                .setPositiveButton("Ok", null)
                .create()
                .show()
        }
    }

    private fun validaNota(): Boolean {
        binding.textinputNotaTitulo.error = if (::notaTitulo.isInitialized && notaTitulo.isNotBlank())
            null else "Insira um título para a nota"
        binding.textinputNotaData.error = if (::dateSelected.isInitialized)
            null else "Selecione uma data"
        binding.textinputNotaHora.error = if (::timeSelected.isInitialized)
            null else "Selecione um horário"

        return listOf(binding.textinputNotaTitulo,
            binding.textinputNotaData,
            binding.textinputNotaTitulo).all { it.error == null }
    }
}