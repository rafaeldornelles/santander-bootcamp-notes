package br.com.rafaeldornelles.ui.listNotas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.databinding.ItemNotaBinding
import br.com.rafaeldornelles.model.Nota

class NotasAdapter(val notas: List<Nota>): RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        var binding = ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val nota = notas[position]
        holder.bind(nota)
    }

    override fun getItemCount() = notas.size

    class NotasViewHolder(binding: ItemNotaBinding): RecyclerView.ViewHolder(binding.root) {
        val tvTitulo = binding.notaItemTitulo
        val tvData = binding.notaItemData

        fun bind(nota: Nota){
            val notaDataText = "${nota.data.format(NotasApplication.dateFormatter)} ${nota.horario.format(NotasApplication.timeFormatter)}"
            tvTitulo.text = nota.titulo
            tvData.text = notaDataText
        }
    }
}