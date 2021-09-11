package br.com.rafaeldornelles.ui.listNotas

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

class NotasFormFragment: Fragment(), NotasListener {
    lateinit var binding: FragmentNotasFormBinding
    private val DATEPICKER_TAG = "datepicker"
    private val TIMEPICKER_TAG = "timepicker"

    private val notasViewModel: NotasViewModel by viewModels { NotasViewModelFactory(NotasApplication.instance.repository) }

    private val datePicker by lazy { MaterialDatePicker.Builder.datePicker().build() }
    private val timePicker by lazy { MaterialTimePicker.Builder().build() }

    lateinit var notaTitulo: String
    lateinit var dateSelected: LocalDate
    lateinit var timeSelected: LocalTime

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNotasFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarDatePicker()
        configurarTimePicker()
        configurarBotaoCancelar()
        configurarBotaoSalvar()
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
            setOnFocusChangeListener { v, hasFocus ->
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
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) showTimePicker()
            }
        }

        timePicker.addOnPositiveButtonClickListener {
            timeSelected = LocalTime.of(timePicker.hour, timePicker.minute)
            binding.textinputNotaHora.setText(timeSelected.format(NotasApplication.timeFormatter))
        }
    }

    fun showDatePicker(){
        if (!datePicker.isAdded) datePicker.show(parentFragmentManager, DATEPICKER_TAG)
    }

    fun showTimePicker(){
        if (!timePicker.isAdded) timePicker.show(parentFragmentManager, TIMEPICKER_TAG)
    }

    fun salvarNota(){
        notaTitulo = binding.textinputNotaTitulo.editText?.text?.toString()?:""
        if (!validaNota()) return
        val nota = Nota(0, notaTitulo, dateSelected, timeSelected)
        notasViewModel.insert(nota, this)
    }

    fun validaNota(): Boolean {
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

    override fun onInsertSuccess() {
        findNavController().popBackStack()
    }

    override fun onInsertError() {
        AlertDialog.Builder(context)
            .setTitle("Erro ao salvar nota!")
            .setMessage("Não foi possível salvar a nota. Tente novamente mais tarde.")
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }
}