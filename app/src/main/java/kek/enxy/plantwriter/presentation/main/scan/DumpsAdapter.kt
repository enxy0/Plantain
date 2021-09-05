package kek.enxy.plantwriter.presentation.main.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.plantwriter.databinding.ItemDumpsBinding

class DumpsAdapter(
    private val onDumpsClicked: () -> Unit
) : ListAdapter<Unit, DumpsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Unit>() {
        override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
        override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDumpsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onDumpsClicked)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit

    class ViewHolder(binding: ItemDumpsBinding, onDumpsClicked: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewDumps.setOnClickListener { onDumpsClicked() }
        }
    }
}
