package kek.enxy.plantwriter.presentation.main.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.plantwriter.databinding.ItemPlantainBinding

class PlantainAdapter : ListAdapter<Unit, PlantainAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Unit>() {
        override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
        override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit

    class ViewHolder(binding: ItemPlantainBinding) : RecyclerView.ViewHolder(binding.root)
}
