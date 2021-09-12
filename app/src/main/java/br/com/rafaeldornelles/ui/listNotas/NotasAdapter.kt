package br.com.rafaeldornelles.ui.listNotas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.rafaeldornelles.NotasApplication
import br.com.rafaeldornelles.R
import br.com.rafaeldornelles.databinding.ItemNotaBinding
import br.com.rafaeldornelles.model.Nota

class NotasAdapter(private val notas: List<Nota>, private val listener: NotasAdapterListener): RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val binding = ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val nota = notas[position]
        holder.bind(nota, listener)
        holder.itemView.setOnClickListener {
            listener.onItemClick(nota)
        }
    }

    override fun getItemCount() = notas.size

    class NotasViewHolder(binding: ItemNotaBinding): RecyclerView.ViewHolder(binding.root) {
        private val tvTitulo = binding.notaItemTitulo
        private val tvData = binding.notaItemData
        private val ivMore = binding.notaItemMore

        fun bind(nota: Nota, listener: NotasAdapterListener){
            val notaDataText = "${nota.data.format(NotasApplication.dateFormatter)} ${nota.horario.format(NotasApplication.timeFormatter)}"
            tvTitulo.text = nota.titulo
            tvData.text = notaDataText
            ivMore.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    menuInflater.inflate(R.menu.menu_nota_item_options, menu)
                    setOnMenuItemClickListener { menu ->
                        when(menu.itemId){
                            R.id.menu_nota_remover -> {
                                listener.onDelete(nota)
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                    show()
                }
            }
        }
    }

    interface NotasAdapterListener{
        fun onDelete(nota: Nota)
        fun onItemClick(nota: Nota)
    }
}