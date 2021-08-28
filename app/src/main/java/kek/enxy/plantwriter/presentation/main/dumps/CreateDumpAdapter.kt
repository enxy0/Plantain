package kek.enxy.plantwriter.presentation.main.dumps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.plantwriter.databinding.ItemCreateDumpBinding

class CreateDumpAdapter(
    private val listener: CreateDumpListener
) : ListAdapter<Unit, CreateDumpAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Unit>() {
        override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean = true

        override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCreateDumpBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit

    class ViewHolder(
        binding: ItemCreateDumpBinding,
        private val listener: CreateDumpListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onCreateDumpClicked()
            }
        }
    }

    fun interface CreateDumpListener {
        fun onCreateDumpClicked()
    }
}
